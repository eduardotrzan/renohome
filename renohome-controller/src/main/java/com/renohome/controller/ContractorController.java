package com.renohome.controller;

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

import com.renohome.dto.request.ContractorCreateDto;
import com.renohome.dto.response.ContractorDto;
import com.renohome.service.aggregator.ContractorMediator;
import com.renohome.service.validation.exception.NotFoundException;

@RestController
@RequestMapping({ "/v1/contractors" })
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorMediator contractorMediator;

    @PermitAll
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractorDto create(@Validated @RequestBody ContractorCreateDto request) {
        return this.contractorMediator.create(request);
    }

    @PermitAll
    @GetMapping(value = "/{contractorUuid}", produces = "application/json")
    public ContractorDto findComment(@PathVariable(value = "contractorUuid") UUID contractorUuid) {
        return this.contractorMediator.findByUuid(contractorUuid)
                .orElseThrow(() -> new NotFoundException(ContractorDto.class.getSimpleName()));
    }

}
