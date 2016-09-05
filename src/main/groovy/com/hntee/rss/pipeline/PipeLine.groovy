package com.hntee.rss.pipeline

import groovy.util.logging.Log
import groovy.xml.XmlUtil
/**
 * Created by htan on 2016/8/5.
 */

@Log
class PipeLine {
    def rss

    // 过滤掉含有以下关键词的内容
    def filter(String location, String[] excludeWords) {
        if (excludeWords == null)
            return this

        excludeWords.each { word ->
            rss.items.findAll {
                def text = it[location].toString();
                text.contains(word)
            }.replaceNode {}
        }
        return this
    }

    // 文本中必须含有所有关键词
    def must(String location, String[] mustWords) {
        if (mustWords == null)
            return this

        mustWords.each { word ->
            rss.items.findAll {
                def text = it[location].toString();
                !text.contains(word)
            }.replaceNode {}
        }
        return this
    }

    // 文本必须大于指定字数
    def moreThan(String location, Integer length) {
        if (length == null)
            return this

        rss.items.findAll {
            def text = it[location].toString();
            text.length() < length
        }.replaceNode {}
        return this
    }

    // 文本中含有任意一个关键词都可纳入
    def any(String location, String[] anyWords) {
        if (anyWords == null)
            return this

        rss.items.findAll {
            def text = it[location].toString();
            def satisfied = anyWords.any  {
                text.contains(it)
            }
            !satisfied
        }.replaceNode {}

        return this
    }

    // 去除一些固定的字符串
    def remove(String location, String exclude) {
        return replace(location, exclude, "")
    }

    // 替换一些固定的字符串
    def replace(String location, String exclude, String target) {
        if (exclude == null)
            return this

        rss.items.collect {
            it[location] = it[location].toString().replace(exclude, target)
            return it
        }
        return this
    }

    def toXML() {
        // 替换unicode转换之后的乱码, 如&#55357;
        def xml = XmlUtil.serialize(rss.rss).replaceAll(/&#\d{5};/,"")
        return xml
    }

    def PipeLine(rss) {
        this.rss = rss
    }
}
