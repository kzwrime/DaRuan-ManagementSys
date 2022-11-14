package com.backend_pg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class IntegratedWebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    LoggingInterceptor loggingInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        super.addInterceptors(registry);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8) {
            {
                ArrayList<MediaType> list = new ArrayList<>();
                list.add(MediaType.APPLICATION_JSON);
                list.add(MediaType.ALL);
                setSupportedMediaTypes(list);
            }
        });
        super.configureMessageConverters(converters);
    }
}
