package com.hntee.rss.weibo

import com.hntee.rss.RSS
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import org.xml.sax.SAXParseException

import java.rmi.RemoteException

import static groovyx.gpars.GParsPool.withPool
import static groovyx.net.http.ContentType.TEXT

/**
 * Created by htan on 2016/8/12.
 */
class WeiboRSS implements RSS{
    def rss
    def url
    @Lazy def items = rss.channel.item

    def getTitles() {
        items.title.collect { it.toString() }
    }

    def getDescriptions() {
        items.description.collect { it.toString() }
    }

    def size() {
        getTitles().size()
    }

    def WeiboRSS(String userId) {
        def http = new HTTPBuilder('http://rss.weibodangan.com')
        def path = "/weibo/rss/${userId}/"
        http.get(path: path,
                contentType: TEXT,
                headers: ['User-Agent': "Apache HTTPClient"],
                ){ response, stream ->
            def rssText = stream.text
            try {
                rss = new XmlSlurper().parseText(rssText)
            } catch (MissingPropertyException e) {
                System.err.println rssText
                throw new Error("解析错误，因为远程服务器返回的数据有误\nURL: ${url}")
            }
        }

        // 获取每一条正文的全文
        withPool(10) {
            items.collectParallel {
                it.description = FullText.expand(it.description)
                it.title = it.description.text()
                return it
            }
        }
    }

    def WeiboRSS(param,String type) {
        switch (type) {
            case ~/text/:
                rss = new XmlSlurper().parseText(param)
                break
            case ~/rss/:
                rss = param
                break

        }
    }

    def WeiboRSS() {
    }
}
