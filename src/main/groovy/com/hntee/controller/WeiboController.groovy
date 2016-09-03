package com.hntee.controller

import com.hntee.rss.pipeline.PipeLine
import com.hntee.rss.weibo.WeiboRSS
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by tanhao on 2016/8/11.
 */
@Controller
@RequestMapping(value = "/weibo")
class WeiboController {
    @RequestMapping(value = "{user}", produces = "application/xml; charset=utf-8")
    def @ResponseBody rss(@PathVariable String user,
                          @RequestParam(value = "filter", required = false) String[] filter,
                          @RequestParam(value = "remove", required = false) String remove,
                          @RequestParam(value = "must", required = false) String[] must,
                          @RequestParam(value = "any", required = false) String[] any,
                          @RequestParam(value = "moreThan", required = false) int length) {
        def weiboRSS = new WeiboRSS(user)
        def pipeLine = new PipeLine(weiboRSS)

        return pipeLine
                .filter('title', filter)
                .remove("description", remove)
                .must("description", must)
                .any("description", any)
                .moreThan("title", length)
                .toXML()

    }
}