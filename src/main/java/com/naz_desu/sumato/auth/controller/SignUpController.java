package com.naz_desu.sumato.auth.controller;

import com.naz_desu.sumato.auth.model.PostSignUpRequest;
import com.naz_desu.sumato.auth.service.CreateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignUpController {
    private final CreateUserService createUserService;

    @PostMapping("/log-in")
    public Map<String, Long> logIn(@RequestBody PostSignUpRequest request) {
        log.info("First log in request for user:\n{}", request);
        return Collections.singletonMap("userId", createUserService.createUser(request));
    }

}
