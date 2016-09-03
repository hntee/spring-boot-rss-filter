package com.hntee.rss.xueqiu

import groovyx.net.http.HTTPBuilder

import java.rmi.RemoteException

/**
 * Created by htan on 2016/8/25.
 */
class JS2XML {
    def static parse = { json ->
        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)

        xml.rss(version:'2.0') {

            channel {
                updated new Date()
                // add the top level information about this feed.
                title("My super atom feed")
                subtitle("Serving up my content")
                id("uri:uuid:xxx-xxx-xxx-xxx")
                link(href:"http://www.thecoderscorner.com")
                author {
                    name("MyName")
                }

                // for each entry we need to create an entry element
                json.statuses.each { item ->
                    items {
                        title item.description
                        updated item.edited_at
                        description item.text;
                    }
                }
            }

        }
        print writer.toString();
        return writer.toString();


    }
}
