package com.hntee

import com.hntee.rss.pipeline.PipeLine
import com.hntee.rss.weibo.WeiboRSS
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by tanhao on 2016/8/11.
 */
@Controller
@RequestMapping(value = "/weibo")
class WeiboController {
    @RequestMapping(value = "/")
    def @ResponseBody hello() {
        return "Hello World!"
    }


    @RequestMapping(value = "{user}")
    def @ResponseBody rss(@PathVariable String user) {
        def weiboRSS = new WeiboRSS(user)
        def pipeLine = new PipeLine(weiboRSS)
        return pipeLine.toXML()
    }
}