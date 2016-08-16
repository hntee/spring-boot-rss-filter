package com.hntee.controller

import com.hntee.rss.pipeline.PipeLine
import com.hntee.rss.universal.UniversalRSS
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by tanhao on 2016/8/15.
 */
/**
 * Created by tanhao on 2016/8/11.
 */
@Controller
class UniversalController {
    @RequestMapping(value = "/rss", produces = "application/xml; charset=utf-8")
    def @ResponseBody rss(@RequestParam(value = "url") String url,
                          @RequestParam(value = "filter", required = false) String[] filter,
                          @RequestParam(value = "remove", required = false) String remove,
                          @RequestParam(value = "must", required = false) String[] must,
                          @RequestParam(value = "any", required = false) String[] any) {
        def universalRSS = new UniversalRSS(url)
        def pipeLine = new PipeLine(universalRSS)

        return pipeLine
                .filter('title', filter)
                .remove("description", remove)
                .must("title", must)
                .any("title", any)
                .toXML()

    }
}
