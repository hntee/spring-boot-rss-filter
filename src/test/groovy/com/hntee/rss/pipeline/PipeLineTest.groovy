package com.hntee.rss.pipeline

import com.hntee.rss.RSS
import com.hntee.rss.weibo.WeiboRSS
import org.junit.Before
import groovy.mock.interceptor.*
import org.junit.BeforeClass

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*
/**
 * Created by htan on 2016/8/12.
 */
class PipeLineTest extends GroovyTestCase {
    def weiboRSS
    @BeforeClass
    void setUp() {
        def rssText = this.getClass().getResource( '/feed2.xml' ).text
        def rss = new XmlSlurper().parseText(rssText)
        weiboRSS = new WeiboRSS(rssText, 'text')
    }

    void testFilter() {
//        def mockWeiboRSS = new MockFor(WeiboRSS, true)
//        mockWeiboRSS.demand.with {
//            WeiboRSS() { new Expando([rss: rss]) }
//        }
//        mockWeiboRSS.demand.getItems(1..100) { rss.channel.item }
//
//        mockWeiboRSS.use {
//            def mweiboRSS = new WeiboRSS()
//            PipeLine pipeLine = new PipeLine(mweiboRSS)
//            def originSize = mweiboRSS.size()
//            def filteredSize = pipeLine.filter('description','回复').rss.size()
//  //          assert filteredSize < originSize
//        }

        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        pipeLine.filter('description','回复')
        def xmlText = pipeLine.toXML()
        def newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize = newWeiboRSS.size()
        assert filteredSize < originSize

        pipeLine.filter('description','RT')
        xmlText = pipeLine.toXML()
        newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize2 = newWeiboRSS.size()
        assert filteredSize2 < filteredSize
    }

    void testRemove() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        pipeLine.remove('description','美元议息')
        pipeLine.remove('title','美元议息')
        def xmlText = pipeLine.toXML()
        assert !xmlText.contains('美元议息')
    }

    void testReplace() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        pipeLine.replace('description','美元议息','美元9999999999议息')
        pipeLine.replace('title','美元议息','美元xxxxxxxx议息')
        def xmlText = pipeLine.toXML()
        assert xmlText.contains('美元9999999999议息')
        assert xmlText.contains('美元xxxxxxxx议息')
    }

    void testToXML() {

    }
}
