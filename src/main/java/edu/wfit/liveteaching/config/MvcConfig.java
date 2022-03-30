package edu.wfit.liveteaching.config;

import edu.wfit.liveteaching.dao.LiveDao;
import edu.wfit.liveteaching.dao.StudentDao;
import edu.wfit.liveteaching.dao.TeacherDao;
import edu.wfit.liveteaching.interceptor.LiveCountInterceptor;
import edu.wfit.liveteaching.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springboot 请求拦截器
 */
@Configuration
public class MvcConfig {
    @Autowired
    private LiveDao liveDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/login", "/register/**", "/css/**", "/js/**", "/img/**");
                registry.addInterceptor(new LiveCountInterceptor(liveDao, studentDao, teacherDao))
                        .addPathPatterns("/**")
                        .excludePathPatterns("/login", "/register/**", "/css/**", "/js/**", "/img/**");
            }
        };
    }
}
