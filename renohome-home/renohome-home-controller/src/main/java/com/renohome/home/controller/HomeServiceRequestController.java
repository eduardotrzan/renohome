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
import com.renohome.home.dto.request.HomeServiceRequestCreateDto;
import com.renohome.home.dto.response.HomeServiceRequestDto;
import com.renohome.home.service.aggregator.segregational.HomeServiceRequestSegregationalMediator;

@RestController
@RequestMapping({ "/v1" })
@RequiredArgsConstructor
public class HomeServiceRequestController {

    private final HomeServiceRequestSegregationalMediator homeServiceRequestSegregationalMediator;

    @PermitAll
    @PostMapping(value = "/homes/{homeUuid}/homeServiceRequests", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public HomeServiceRequestDto create(@PathVariable(value = "homeUuid") UUID homeUuid,
                                        @Validated @RequestBody HomeServiceRequestCreateDto request) {
        return this.homeServiceRequestSegregationalMediator.create(homeUuid, request);
    }

    @PermitAll
    @GetMapping(value = "/homeServiceRequests/{homeServiceRequestUuid}", produces = "application/json")
    public HomeServiceRequestDto findComment(@PathVariable(value = "homeServiceRequestUuid") UUID homeServiceRequestUuid) {
        return this.homeServiceRequestSegregationalMediator.findByUuid(homeServiceRequestUuid)
                .orElseThrow(() -> new NotFoundException(HomeServiceRequestDto.class.getSimpleName()));
    }

}
