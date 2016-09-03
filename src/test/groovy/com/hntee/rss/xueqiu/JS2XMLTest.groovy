package com.hntee.rss.xueqiu

/**
 * Created by htan on 2016/8/25.
 */
class JS2XMLTest extends GroovyTestCase {
    void testParse() {
        def json = new XueqiuRSS().json
        JS2XML.parse(json)
    }
}
