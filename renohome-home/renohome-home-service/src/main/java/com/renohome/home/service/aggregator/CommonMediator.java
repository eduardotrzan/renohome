package com.renohome.home.service.aggregator;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.generic.expcetions.BadRequestException;
import com.renohome.generic.expcetions.NotFoundException;
import com.renohome.home.domain.entity.Service;
import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.dto.enums.ServiceTypeDto;
import com.renohome.home.service.business.ServiceService;

@RequiredArgsConstructor
@Component
class CommonMediator {

    private final ServiceService serviceService;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    Service findServiceByType(ServiceTypeDto requestServiceType) {
        if (requestServiceType == null) {
            throw new BadRequestException(ServiceType.class.getSimpleName());
        }

        ServiceType serviceType = ServiceType.valueOf(requestServiceType.name());
        return this.serviceService.findByType(serviceType)
                .orElseThrow(() -> new NotFoundException(ServiceType.class.getSimpleName()));
    }

}