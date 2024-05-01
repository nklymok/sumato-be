package com.naz_desu.sumato.common.config.resolver;

import com.naz_desu.sumato.common.config.dto.UserIdDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Strings.isNotBlank(jwt.getSubject());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authId = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject();
        return new UserIdDto(authId);
    }
}
