package com.renohome.contractor.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.contractor.domain.entity.Contractor;
import com.renohome.contractor.domain.entity.enums.ContractorServiceType;
import com.renohome.contractor.dto.enums.ContractorServiceTypeDto;
import com.renohome.contractor.dto.request.ContractorCreateDto;
import com.renohome.contractor.dto.response.ContractorDto;
import com.renohome.contractor.service.business.ContractorService;
import com.renohome.contractor.service.mapper.ContractorMapper;
import com.renohome.generic.expcetions.BadRequestException;
import com.renohome.generic.expcetions.NotFoundException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContractorMediator {

    private final ContractorService contractorService;

    private final ContractorMapper contractorMapper;

    @Transactional
    public ContractorDto create(ContractorCreateDto request) {
        log.info("Create contractor request={}", request);
        Contractor entity = this.contractorMapper.toNewEntity(request);

        ContractorServiceType serviceType = this.convertServiceType(request.getServiceType());
        entity.setServiceType(serviceType);

        Contractor savedEntity = this.contractorService.create(entity);

        return this.contractorMapper
                .toDto(savedEntity)
                .orElseThrow(() -> new NotFoundException(Contractor.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public Optional<ContractorDto> findByUuid(UUID uuid) {
        log.info("Find contractor uuid={}", uuid);
        return this.contractorService
                .findByUuid(uuid)
                .flatMap(contractorMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ContractorDto> listByServiceType(ContractorServiceTypeDto serviceTypeDto) {
        log.info("List contractor by serviceTypeDto={}", serviceTypeDto);
        ContractorServiceType serviceType = this.convertServiceType(serviceTypeDto);
        List<Contractor> contractors = this.contractorService.listByServiceType(serviceType);
        return contractorMapper.toDtos(contractors);
    }

    private ContractorServiceType convertServiceType(ContractorServiceTypeDto serviceTypeDto) {
        return Optional.ofNullable(serviceTypeDto)
                .map(Enum::name)
                .map(ContractorServiceType::valueOf)
                .orElseThrow(() -> new BadRequestException(ContractorServiceTypeDto.class.getSimpleName()));
    }
}
