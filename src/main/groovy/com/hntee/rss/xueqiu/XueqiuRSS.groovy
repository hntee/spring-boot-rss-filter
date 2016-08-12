package com.hntee.rss.xueqiu

import com.hntee.rss.RSS

/**
 * Created by htan on 2016/8/12.
 */
class XueqiuRSS implements RSS{
    def rss
    @Lazy def items = rss.channel.item

    def XueqiuRSS(String userId) {

    }
}
