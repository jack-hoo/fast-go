//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.config;

import com.flyingcow.fastgo.server.common.filters.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

@Configuration
public class XSSFilterConfig {
    public XSSFilterConfig() {
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[0]);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(new String[]{"/*"});
        registration.setName("xssFilter");
        registration.setOrder(2147483647);
        return registration;
    }
}
