package com.hntee.rss.pipeline

/**
 * Created by htan on 2016/8/5.
 */

interface PipeLine {
    def url
    def processTitle()
    def processBody()
    def filter()
}