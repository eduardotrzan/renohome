package com.renohome.contractor.service.aggregator;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.contractor.domain.entity.Contractor;
import com.renohome.contractor.domain.entity.enums.ContractorServiceType;
import com.renohome.contractor.dto.request.ContractorCreateDto;
import com.renohome.contractor.dto.response.ContractorDto;
import com.renohome.contractor.service.business.ContractorService;
import com.renohome.contractor.service.mapper.ContractorMapper;
import com.renohome.generic.expcetions.NotFoundException;


@RequiredArgsConstructor
@Component
public class ContractorMediator {

    private final ContractorService contractorService;

    private final ContractorMapper contractorMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public ContractorDto create(ContractorCreateDto request) {
        Contractor entity = this.contractorMapper.toNewEntity(request);

        ContractorServiceType serviceType = request.getServiceType() == null
                ? null
                : ContractorServiceType.valueOf(request.getServiceType().name());
        entity.setServiceType(serviceType);

        Contractor savedEntity = this.contractorService.create(entity);

        return this.contractorMapper
                .toDto(savedEntity)
                .orElseThrow(() -> new NotFoundException(Contractor.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public Optional<ContractorDto> findByUuid(UUID uuid) {
        return this.contractorService
                .findByUuid(uuid)
                .flatMap(contractorMapper::toDto);
    }
}
