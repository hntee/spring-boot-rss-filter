package com.hntee.rss.weibo

import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*
/**
 * Created by htan on 2016/8/12.
 */
class WeiboRSSTest extends GroovyTestCase {
    @Test
    void testWeiboRSS() {
        def rss = new WeiboRSS("1670458304")
        def titles = rss.items.title.collect { it.toString() }
        def contents = rss.items.description.collect { it.toString() }
        assert (titles.size() > 1)
        assert (contents.size() > 1)
        assert (titles.size() == contents.size())
        assert (contents.get(0).size() > 1)
    }
}
