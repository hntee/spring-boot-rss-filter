package com.hntee.rss.universal

import com.github.kevinsawicki.http.HttpRequest
import com.hntee.rss.RSS
import org.xml.sax.SAXParseException
/**
 * Created by tanhao on 2016/8/15.
 */
class UniversalRSS implements RSS{
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

    def UniversalRSS(String url) {
        this.url = url
        HttpRequest request = HttpRequest.get(url).trustAllCerts().trustAllHosts();
        def rssText = request.body()
        try {
            rss = new XmlSlurper().parseText(rssText)
        } catch (SAXParseException e) {
            System.err.println rssText
            throw new Error("解析错误，因为远程服务器返回的数据有误\nURL: ${url}")
        }
    }
}
