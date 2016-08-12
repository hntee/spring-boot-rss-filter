package com.hntee

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by tanhao on 2016/8/11.
 */
@Controller
@RequestMapping(value = "/weibo")
class DemoController {
    @RequestMapping(value = "/")
    def @ResponseBody hello() {
        return "Hello World!"
    }


    @RequestMapping(value = "{user}")
    def @ResponseBody rss(@PathVariable String user) {
        def url = "http://rss.weibodangan.com/weibo/rss/${user}/"

    }
}