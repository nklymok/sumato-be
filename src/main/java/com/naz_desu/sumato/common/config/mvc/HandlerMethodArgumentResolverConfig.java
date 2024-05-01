package com.naz_desu.sumato.common.config.mvc;

import com.naz_desu.sumato.common.config.resolver.UserIdHandlerMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class HandlerMethodArgumentResolverConfig implements WebMvcConfigurer {

    private final UserIdHandlerMethodArgumentResolver userIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userIdArgumentResolver);
    }

}
