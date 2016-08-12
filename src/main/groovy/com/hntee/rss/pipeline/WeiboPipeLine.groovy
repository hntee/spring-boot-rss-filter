package com.hntee.rss.pipeline

import com.hntee.rss.FullText
import groovy.util.logging.Log
import groovy.xml.XmlUtil

/**
 * Created by htan on 2016/8/5.
 */

@Log
class WeiboPipeLine implements PipeLine{
    private def rss
    private def url
    @Override
    def processTitle() {
        // 处理一下标题
        rss.channel.item.collect {
            if (it.title.toString().length() > 100)
                it.title = it.title.toString().substring(0,100)+"..."
            return it
        }
        return this
    }

    @Override
    def processBody() {
        rss.channel.item.collect {
            it.description = FullText.expand(it.description)
            return it
        }
        return this
    }

    @Override
    def filter() {
        // 把带这两个词的去掉
        rss.channel.item.findAll {
            it.description.toString().contains("回复") || it.description.toString().contains("RT")
        }.replaceNode {}
        return this
    }

    def toXML() {
        log.info "Processing ${url}"
        this.filter().processBody().processTitle()
        def xml = XmlUtil.serialize(rss)
        println xml
        return xml
    }

    def WeiboPipeLine(String url) {
        if (!url.contains("http"))
            url = "http://"+url
        this.url = url
        log.info "Initializing ${url}"
        def rssText = url.toURL().getText('utf-8')
        def rss = new XmlSlurper().parseText(rssText)
        this.rss = rss
    }

    def WeiboPipeLine(File file) {
        this.url = File.toString()
        def rss = new XmlSlurper().parseText(file.text)
        this.rss = rss
    }

}
