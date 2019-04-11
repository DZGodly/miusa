package pers.fairy.miusa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.fairy.miusa.interceptor.AccessLimitInterceptor;
import pers.fairy.miusa.interceptor.PassportInterceptor;


/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 18:46
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor).excludePathPatterns("/login/**", "/static/**", "/goods/to_list");
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/miusa/**");
    }

}
