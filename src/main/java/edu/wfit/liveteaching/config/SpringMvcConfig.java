package edu.wfit.liveteaching.config;

import edu.wfit.liveteaching.interceptor.MySessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Bean
    public ServletListenerRegistrationBean getListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean=new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MySessionListener());
        return servletListenerRegistrationBean;
    }

}
