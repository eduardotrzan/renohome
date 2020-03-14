package com.renohome.home.service.business.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.service.business.external.dto.ContractorExternalDto;
import com.renohome.home.service.config.ExternalApiConfig;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.NEVER)
public class ContractorService {

    private final ExternalApiConfig externalApiConfig;

    private final RestTemplate restTemplate;

    public List<ContractorExternalDto> findContractor(ServiceType serviceType) {
        ContractorExternalDto[] contractors = restTemplate.getForObject(
                this.getFindContractorByServiceUrl(),
                ContractorExternalDto[].class,
                serviceType);
        return Arrays.asList(contractors);
    }

    private String getFindContractorByServiceUrl() {
        return String.format("%s/v1/contractors?serviceType={serviceType}", this.externalApiConfig.getContractorUrl());
    }
}
