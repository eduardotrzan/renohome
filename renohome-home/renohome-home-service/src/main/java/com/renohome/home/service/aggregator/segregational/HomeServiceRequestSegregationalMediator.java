package com.renohome.home.service.aggregator.segregational;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.generic.expcetions.BadRequestException;
import com.renohome.generic.expcetions.InternalServerException;
import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.dto.request.HomeServiceRequestCreateDto;
import com.renohome.home.dto.response.HomeServiceRequestDto;
import com.renohome.home.service.aggregator.transactional.HomeServiceRequestTransactionalMediator;
import com.renohome.home.service.business.external.ContractorService;
import com.renohome.home.service.business.external.dto.ContractorExternalDto;

@Slf4j
@RequiredArgsConstructor
@Component
public class HomeServiceRequestSegregationalMediator {

    private final HomeServiceRequestTransactionalMediator homeServiceRequestTransactionalMediator;

    private final ContractorService contractorService;

    @Transactional(propagation = Propagation.NEVER)
    public HomeServiceRequestDto create(UUID homeUuid, HomeServiceRequestCreateDto request) {
        ServiceType serviceType = Optional.of(request.getServiceType())
                .map(Enum::name)
                .map(ServiceType::valueOf)
                .orElseThrow(() -> new BadRequestException(ServiceType.class.getSimpleName()));

        List<ContractorExternalDto> contractors = this.contractorService.findContractor(serviceType);
        UUID contractorUuid = Objects.requireNonNullElse(contractors, Collections.<ContractorExternalDto>emptyList())
                .stream()
                .sorted(Comparator.comparing(ContractorExternalDto::getCost))
                .map(ContractorExternalDto::getUuid)
                .findFirst()
                .orElseThrow(() -> new InternalServerException("No available contractors for requested service."));

        return this.homeServiceRequestTransactionalMediator.create(homeUuid, contractorUuid, request);
    }

    @Transactional(propagation = Propagation.NEVER)
    public Optional<HomeServiceRequestDto> findByUuid(UUID uuid) {
        return this.homeServiceRequestTransactionalMediator.findByUuid(uuid);
    }
}
