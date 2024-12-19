package com.haitao.controller;

import com.haitao.util.I18nUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class FirstUserController {

    private final HttpServletRequest request;

    @GetMapping("/i18n")
    public String i18n() {
        Locale locale = LocaleContextHolder.getLocale();
        String message1 = I18nUtil.getMessage("A00001");
        String message2 = I18nUtil.getMessage("A00002", request.getHeader("lang"));
        return message1 + message2;
    }
}

