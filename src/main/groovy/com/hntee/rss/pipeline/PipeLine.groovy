package com.hntee.rss.pipeline

import com.hntee.rss.RSS
import com.hntee.rss.weibo.FullText
import groovy.util.logging.Log
import groovy.xml.XmlUtil

/**
 * Created by htan on 2016/8/5.
 */

@Log
class PipeLine {
    def rss

    // 过滤包含这些字符串的条目
    def filter(String location, String exclude) {
        rss.items.findAll {
            def text = it[location].toString();
            text.contains(exclude)
        }.replaceNode {}
        return this
    }

    // 去除一些固定的字符串
    def remove(String location, String exclude) {
        return replace(location, exclude, "")
    }

    // 替换一些固定的字符串
    def replace(String location, String exclude, String target) {
        rss.items.collect {
            it[location] = it[location].toString().replace(exclude, target)
            return it
        }
        return this
    }

    def toXML() {
        def xml = XmlUtil.serialize(rss.rss)
        return xml
    }

    def PipeLine(rss) {
        this.rss = rss
    }
}
