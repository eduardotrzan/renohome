package com.renohome.service.business;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.enums.ServiceType;
import com.renohome.domain.repo.ServiceRepository;

@RequiredArgsConstructor
@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public com.renohome.domain.entity.Service create(com.renohome.domain.entity.Service entity) {
        return this.serviceRepository.save(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<com.renohome.domain.entity.Service> findByUuid(UUID uuid) {
        return this.serviceRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<com.renohome.domain.entity.Service> findByType(ServiceType type) {
        return this.serviceRepository.findByType(type);
    }
}
