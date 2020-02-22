package com.renohome.service.aggregator;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Contractor;
import com.renohome.domain.entity.Home;
import com.renohome.domain.entity.Service;
import com.renohome.dto.enums.ServiceTypeDto;
import com.renohome.dto.request.ContractorCreateDto;
import com.renohome.dto.response.ContractorDto;
import com.renohome.service.business.ContractorService;
import com.renohome.service.mapper.ContractorMapper;
import com.renohome.service.validation.exception.NotFoundException;

@RequiredArgsConstructor
@Component
public class ContractorMediator {

    private final ContractorService contractorService;
    private final CommonMediator commonMediator;

    private final ContractorMapper contractorMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public ContractorDto create(ContractorCreateDto request) {
        Contractor entity = this.contractorMapper.toNewEntity(request);

        ServiceTypeDto requestServiceType = request.getServiceType();
        Service service = this.commonMediator.findServiceByType(requestServiceType);
        entity.setService(service);

        Contractor savedEntity = this.contractorService.create(entity);

        return this.contractorMapper
                .toDto(savedEntity)
                .orElseThrow(() -> new NotFoundException(Home.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public Optional<ContractorDto> findByUuid(UUID uuid) {
        return this.contractorService
                .findByUuid(uuid)
                .flatMap(contractorMapper::toDto);
    }
}
