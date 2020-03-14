package com.renohome.home.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.generic.expcetions.BadRequestException;
import com.renohome.generic.expcetions.NotFoundException;
import com.renohome.home.domain.entity.Home;
import com.renohome.home.domain.entity.HomeServiceRequest;
import com.renohome.home.domain.entity.Service;
import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.dto.enums.ServiceTypeDto;
import com.renohome.home.dto.request.HomeServiceRequestCreateDto;
import com.renohome.home.dto.response.HomeServiceRequestDto;
import com.renohome.home.service.business.internal.HomeService;
import com.renohome.home.service.business.internal.HomeServiceRequestService;
import com.renohome.home.service.business.internal.ServiceService;
import com.renohome.home.service.mapper.HomeServiceRequestMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class HomeServiceRequestTransactionalMediator {

    private final HomeServiceRequestService homeServiceRequestService;
    private final HomeService homeService;
    private final ServiceService serviceService;

    private final HomeServiceRequestMapper homeServiceRequestMapper;

    @Transactional
    public HomeServiceRequestDto create(UUID homeUuid, UUID contractorUuid, HomeServiceRequestCreateDto request) {
        log.info("Creating home service request for homeUuid={} and request={}.",
                 homeUuid, request);
        HomeServiceRequest entity = this.homeServiceRequestMapper.toNewEntity(request);
        entity.setContractorUuid(contractorUuid);

        ServiceTypeDto requestServiceType = request.getServiceType();
        Service service = this.findServiceByType(requestServiceType);
        entity.setService(service);

        Home home = this.homeService.findByUuid(homeUuid)
                .orElseThrow(() -> new NotFoundException(Home.class.getSimpleName()));
        entity.setHome(home);

        HomeServiceRequest savedEntity = this.homeServiceRequestService.create(entity);

        HomeServiceRequestDto dto = this.homeServiceRequestMapper
                .toDto(savedEntity)
                .orElseThrow(() -> new NotFoundException(HomeServiceRequest.class.getSimpleName()));

        log.info("Created home service request as dto={}.", dto);
        return dto;
    }

    private Service findServiceByType(ServiceTypeDto requestServiceType) {
        if (requestServiceType == null) {
            throw new BadRequestException(ServiceType.class.getSimpleName());
        }

        ServiceType serviceType = ServiceType.valueOf(requestServiceType.name());
        return this.serviceService.findByType(serviceType)
                .orElseThrow(() -> new NotFoundException(ServiceType.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public Optional<HomeServiceRequestDto> findByUuid(UUID uuid) {
        return this.homeServiceRequestService
                .findByUuid(uuid)
                .flatMap(homeServiceRequestMapper::toDto);
    }
}
