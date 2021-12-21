package com.config;

import com.filter.PtAdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：     平台管理员过滤器的配置
 */
@Configuration
public class PtAdminFilterConfig {

    @Bean
    public PtAdminFilter ptAdminFilter() {
        return new PtAdminFilter();
    }

    @Bean(name = "ptAdminFilterConf")
    public FilterRegistrationBean ptAdminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(ptAdminFilter());

        /**项目完成时再把注解打开*/

        filterRegistrationBean.addUrlPatterns("/ptAdmin/category/*");
        filterRegistrationBean.addUrlPatterns("/ptAdmin/product/*");
        filterRegistrationBean.addUrlPatterns("/ptAdmin/order/*");


        filterRegistrationBean.setName("ptAdminFilterConf");
        return filterRegistrationBean;
    }

}
