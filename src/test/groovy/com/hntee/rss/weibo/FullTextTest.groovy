package com.hntee.rss.weibo

/**
 * Created by htan on 2016/8/12.
 */

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

class FullTextTest extends GroovyTestCase {
    void testLookUpFullText() {
        def fullText = FullText.lookUpFullText(1670458304, 4005046032872121)
        assert (fullText.toString().contains("新进入市场的，一般服务都好，以买房为例，很多楼开盘，配置的人跟服务是最好的，保安"));
    }

    void testExpand() {
        def message = '忽悠你存钱<a class="btn href longtext" target="_blank"  uid="1670458304" mid="4005046032872121">全文</a>  <br/><br/>'
        def fullText = FullText.expand(message)
        assert (fullText.toString().contains("新进入市场的，一般服务都好，以买房为例，很多楼开盘，配置的人跟服务是最好的，保安"));
    }
}
