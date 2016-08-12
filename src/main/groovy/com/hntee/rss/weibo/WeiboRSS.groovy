package com.hntee.rss.weibo

import com.hntee.rss.RSS

/**
 * Created by htan on 2016/8/12.
 */
class WeiboRSS implements RSS{
    def rss
    @Lazy def items = rss.channel.item

    def WeiboRSS(String userId) {
        def url = "http://rss.weibodangan.com/weibo/rss/${userId}/"
        def rssText = url.toURL().getText('utf-8')
        rss = new XmlSlurper().parseText(rssText)
        // 获取每一条正文的全文
        rss.channel.item.collect {
            it.description = FullText.expand(it.description)
            return it
        }
    }
}
