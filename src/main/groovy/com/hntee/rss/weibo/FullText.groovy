package com.hntee.rss.weibo

import groovy.json.JsonParserType
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonSlurper

import java.rmi.RemoteException
import java.rmi.ServerError

import static groovyx.net.http.ContentType.TEXT
/**
 * Created by htan on 2016/8/5.
 */
class FullText {
    def static lookUpFullText = { uid, mid ->
        def http = new HTTPBuilder('http://weibo.wbdacdn.com')
        http.get(path: '/api/longtext',
                contentType: TEXT,
                headers: ['User-Agent': "Apache HTTPClient"],
                query:[
                        'uid': uid,
                        'mid': mid
                ]){ response, stream ->
            def jsonText = stream.text

            //Setting the parser type to JsonParserLax
            def parser = new JsonSlurper().setType(JsonParserType.LAX)
            def jsonResp = parser.parseText(jsonText)
            try {
                return jsonResp.msg.toString()
            } catch (MissingPropertyException e) {
                System.err << "返回值错误：" << jsonResp
                throw new RemoteException()
            }
        }
    }

    // generate params from source text like 包括玩存钱业务的，你去发廊理发，忽悠你存钱<a class="btn href longtext" target="_blank"  uid="1670458304" mid="4005046032872121">全文</a>  <br/><br/>
    def static expand = { text ->
        // 首先去找文字中有没有“全文”，如果有的话就提取uid和mid，再从上面的方法中获取全文
        // 如果没有"全文", 就返回经过处理的文字
        def match = text =~ /uid="(\d+)" mid="(\d+)">全文/
        def full = text.toString()
        if (match.find()) {
            def uid = match[0][1]
            def mid = match[0][2]
            try {
                full = FullText.lookUpFullText(uid, mid)
            } catch (RemoteException e) {
                System.err.println "对方服务器出了问题，获取全文失败"
                full = text
            }
        }
        return full
    }
}



