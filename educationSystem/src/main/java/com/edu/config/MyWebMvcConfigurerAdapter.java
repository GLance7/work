package com.edu.config;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

 /*   @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".html");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("file:D:\\vue\\web\\index.html","http://www.xxx.com/");
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }
*/


    /**
     *  * 统一配置url路径 wl
     */
 /*   @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/tologin").setViewName("login");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/error/errordeal").setViewName("error/errordealogin");
        registry.addViewController("/error/unauthorized").setViewName("error/unauthorized");
        registry.addViewController("/main").setViewName("main");
        super.addViewControllers(registry);
    }*/

}

