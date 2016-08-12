package com.hntee.rss.pipeline

import com.hntee.rss.RSS
import com.hntee.rss.weibo.WeiboRSS
import org.junit.Before
import groovy.mock.interceptor.*

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*
/**
 * Created by htan on 2016/8/12.
 */
class PipeLineTest extends GroovyTestCase {
    @Before
    void setUp() {
        def rssText = this.getClass().getResource( '/feed.xml' ).text
        def rss = new XmlSlurper().parseText(rssText)
//        def wbrss = [rss : rss,
//                      items : { ->
//                          rss.channel.item
//                      }
//        ] as WeiboRSS



        def mockWeiboRSS = new MockFor(WeiboRSS)
        mockWeiboRSS.demand.rss { rss }
        mockWeiboRSS.demand.items { rss.channel.item }

        1




        PipeLine pipeLine = new PipeLine(wbrss)
    }
    void testFilter() {
        def contents = rss.items.description.collect { it.toString() }

        assert 1 == 1
    }

    void testRemove() {

    }

    void testReplace() {

    }

    void testToXML() {

    }
}
