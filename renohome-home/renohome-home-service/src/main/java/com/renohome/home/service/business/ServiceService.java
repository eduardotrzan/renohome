package com.renohome.home.service.business;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.domain.repo.ServiceRepository;

@RequiredArgsConstructor
@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public com.renohome.home.domain.entity.Service create(com.renohome.home.domain.entity.Service entity) {
        return this.serviceRepository.save(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<com.renohome.home.domain.entity.Service> findByUuid(UUID uuid) {
        return this.serviceRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<com.renohome.home.domain.entity.Service> findByType(ServiceType type) {
        return this.serviceRepository.findByType(type);
    }
}
