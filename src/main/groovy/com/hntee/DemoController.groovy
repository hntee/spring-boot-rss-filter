package com.hntee

/**
 * Created by tanhao on 2016/8/11.
 */
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class DemoController {
    @RequestMapping(value = "/")
    def @ResponseBody hello() {
        return "Hello World!"
    }

    @RequestMapping(value = "/jj")
    def @ResponseBody jj() {
        return "Hello jj!"
    }
}