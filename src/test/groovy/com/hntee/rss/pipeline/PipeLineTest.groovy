package com.hntee.rss.pipeline

import com.hntee.rss.weibo.WeiboRSS
import org.junit.BeforeClass
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

    void testFilterArray() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        String[] toFilter = ["回复","RT"]
        pipeLine.filter('description',toFilter)
        def xmlText = pipeLine.toXML()
        def newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize = newWeiboRSS.size()
        assert filteredSize == 2

    }

    void testRemove() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        pipeLine.remove('description','美元议息')
        pipeLine.remove('title','美元议息')
        def xmlText = pipeLine.toXML()
        assert !xmlText.contains('美元议息')
    }

    void testRemove2() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        pipeLine.remove('description','<br />')
        pipeLine.remove('title','美元议息')
        def xmlText = pipeLine.toXML()
        assert !xmlText.contains('<br />')
    }

    void testAny() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        def t = ['美元议息','我出门办事'] as String[]
        pipeLine.any('description',t)
        def xmlText = pipeLine.toXML()
        def newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize = newWeiboRSS.size()
        assert filteredSize == 2
    }

    void testMust() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        def t = ['美元议息','本段请勿'] as String[]
        pipeLine.must('description',t)
        def xmlText = pipeLine.toXML()
        def newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize = newWeiboRSS.size()
        assert filteredSize == 1
    }

    void testMust2() {
        def pipeLine = new PipeLine(weiboRSS)
        def originSize = pipeLine.rss.size()
        def t = ['美元议息','本段请勿','黄小样'] as String[]
        pipeLine.must('description',t)
        def xmlText = pipeLine.toXML()
        def newWeiboRSS = new WeiboRSS(xmlText, 'text')
        def filteredSize = newWeiboRSS.size()
        assert filteredSize == 0
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
