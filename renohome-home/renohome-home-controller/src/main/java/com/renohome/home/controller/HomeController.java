package com.renohome.home.controller;

import lombok.RequiredArgsConstructor;

import javax.annotation.security.PermitAll;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.renohome.generic.expcetions.NotFoundException;
import com.renohome.home.dto.request.HomeCreateDto;
import com.renohome.home.dto.response.HomeDto;
import com.renohome.home.service.aggregator.HomeMediator;

@RestController
@RequestMapping({ "/v1/homes" })
@RequiredArgsConstructor
public class HomeController {

    private final HomeMediator homeMediator;

    @PermitAll
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public HomeDto create(@Validated @RequestBody HomeCreateDto request) {
        return this.homeMediator.create(request);
    }

    @PermitAll
    @GetMapping(value = "/{homeUuid}", produces = "application/json")
    public HomeDto findComment(@PathVariable(value = "homeUuid") UUID homeUuid) {
        return this.homeMediator.findByUuid(homeUuid)
                .orElseThrow(() -> new NotFoundException(HomeDto.class.getSimpleName()));
    }

}
