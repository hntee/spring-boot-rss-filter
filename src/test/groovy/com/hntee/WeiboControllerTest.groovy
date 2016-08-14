package com.hntee

import com.hntee.rss.RSS
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by tanhao on 2016/8/13.
 */

class WeiboControllerTest extends Specification {
    @Shared
    ConfigurableApplicationContext context
    def rss

    void setupSpec() {
        Future future = Executors
                .newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    public ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication
                                .run(App.class)
                    }
                })
        context = future.get(60, TimeUnit.SECONDS)
    }

    void setup() {
        def rssText = this.getClass().getResource( '/feed2.xml' ).text
        rss = new XmlSlurper().parseText(rssText)
    }

    void cleanupSpec() {
        if (context != null) {
            context.close()
        }
    }

    void "should return Greetings from Spring Boot!"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8090/weibo/", String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == "Hello World!"
    }

    def "test weibo user"() {
        given:
        RSS weiboRSS = Mock()

        when:
        weiboRSS.rss >> rss
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8090/weibo/1670458304", String.class)
1
        then:
        entity.statusCode == HttpStatus.OK
        entity.body.contains("美元")
    }

}
