package com.hntee.rss.universal

import com.github.kevinsawicki.http.HttpRequest
import org.xml.sax.SAXParseException

/**
 * Created by tanhao on 2016/8/15.
 */
class UniversalRSSTest extends GroovyTestCase {
    void testHttps() {
        def url = 'https://bbs.byr.cn/rss/board-JobInfo'
        // 用com.github.kevinsawicki.http.HttpRequest的包，去除https的错误
        HttpRequest request = HttpRequest.get(url).trustAllCerts().trustAllHosts();
        def rssText = request.body()
        try {
            def rss = new XmlSlurper().parseText(rssText)
        } catch (SAXParseException e) {
            System.err.println rssText
            throw new Error("解析错误，因为远程服务器返回的数据有误\nURL: ${url}")
        }
    }


}
