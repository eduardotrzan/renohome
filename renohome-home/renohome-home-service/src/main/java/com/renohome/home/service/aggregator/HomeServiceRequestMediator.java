package com.renohome.home.service.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.generic.expcetions.NotFoundException;
import com.renohome.home.domain.entity.Home;
import com.renohome.home.domain.entity.HomeServiceRequest;
import com.renohome.home.domain.entity.Service;
import com.renohome.home.dto.enums.ServiceTypeDto;
import com.renohome.home.dto.request.HomeServiceRequestCreateDto;
import com.renohome.home.dto.response.HomeServiceRequestDto;
import com.renohome.home.service.business.HomeService;
import com.renohome.home.service.business.HomeServiceRequestService;
import com.renohome.home.service.mapper.HomeServiceRequestMapper;

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
