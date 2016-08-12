package com.hntee
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by tanhao on 2016/8/11.
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hntee")

class App {
    static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args)
    }
}
