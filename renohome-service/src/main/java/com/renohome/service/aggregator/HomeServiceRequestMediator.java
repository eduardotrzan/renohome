package com.renohome.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Home;
import com.renohome.domain.entity.HomeServiceRequest;
import com.renohome.domain.entity.Service;
import com.renohome.dto.enums.ServiceTypeDto;
import com.renohome.dto.request.HomeServiceRequestCreateDto;
import com.renohome.dto.response.HomeServiceRequestDto;
import com.renohome.service.business.HomeService;
import com.renohome.service.business.HomeServiceRequestService;
import com.renohome.service.mapper.HomeServiceRequestMapper;
import com.renohome.service.validation.exception.NotFoundException;

@Slf4j
@RequiredArgsConstructor
@Component
public class HomeServiceRequestMediator {

    private final HomeServiceRequestService homeServiceRequestService;
    private final HomeService homeService;
    private final CommonMediator commonMediator;

    private final HomeServiceRequestMapper homeServiceRequestMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public HomeServiceRequestDto create(UUID homeUuid, HomeServiceRequestCreateDto request) {
        log.info("Creating home service request for homeUuid={} and request={}.",
                 homeUuid, request);
        HomeServiceRequest entity = this.homeServiceRequestMapper.toNewEntity(request);

        ServiceTypeDto requestServiceType = request.getServiceType();
        Service service = this.commonMediator.findServiceByType(requestServiceType);
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

    @Transactional(readOnly = true)
    public Optional<HomeServiceRequestDto> findByUuid(UUID uuid) {
        return this.homeServiceRequestService
                .findByUuid(uuid)
                .flatMap(homeServiceRequestMapper::toDto);
    }
}
