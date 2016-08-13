package com.hntee.rss.weibo

import com.hntee.rss.RSS
import org.xml.sax.SAXParseException

import java.rmi.ServerError
import java.rmi.ServerException

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
        this.url = "http://rss.weibodangan.com/weibo/rss/${userId}/"
        def rssText = url.toURL().getText('utf-8')
        try {
            rss = new XmlSlurper().parseText(rssText)
        } catch (SAXParseException e) {
            System.err.println rssText
            throw new Error("解析错误，因为远程服务器返回的数据有误")
        }
        // 获取每一条正文的全文
        items.collect {
            it.description = FullText.expand(it.description)
            return it
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
}
