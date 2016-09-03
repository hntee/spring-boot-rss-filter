package com.hntee.rss.xueqiu

import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import java.rmi.RemoteException

import static groovyx.net.http.ContentType.JSON

/**
 * Created by htan on 2016/8/25.
 */
class XueqiuRSSTest extends GroovyTestCase {
    void testRequest() {
        def http = new HTTPBuilder('https://xueqiu.com')
        http.ignoreSSLIssues()
        http.request( Method.GET, ContentType.TEXT ) { req ->
            uri.path = '/v4/statuses/user_timeline.json'
            uri.query = [ user_id : 4776750571 ]
            headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
            headers.'Cookie' = 'xq_a_token=53891f25c00cbc54aa567ee4ce682dee6aa66eeb'
            response.success = { response, stream ->
                def jsonText = stream.text
                //Setting the parser type to JsonParserLax
                def parser = new JsonSlurper().setType(JsonParserType.LAX)
                def jsonResp = parser.parseText(jsonText)
                try {
                    print jsonResp
                    return jsonResp
                } catch (MissingPropertyException e) {
                    System.err.println "返回值错误："
                    System.err.println jsonResp
                    throw new RemoteException()
                }
            }
        }


    }
}