package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.service.UserService;

import java.util.List;

@Api(tags = "Forecasts")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/forecast")
public class ForecastListController {
    private UserService userService;

    @Autowired
    public ForecastListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/urlList")
    public ResponseEntity<List<ForecastDashBoard>> getList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(userService.getForecastUrls(authentication.getName()));
    }


}
