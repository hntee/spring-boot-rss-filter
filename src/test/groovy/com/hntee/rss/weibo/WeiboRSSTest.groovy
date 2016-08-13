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
        def weiboRSS = new WeiboRSS("1670458304")
        assert (weiboRSS.rss.channel.title.text().contains("神嘛事儿"))
    }

}
