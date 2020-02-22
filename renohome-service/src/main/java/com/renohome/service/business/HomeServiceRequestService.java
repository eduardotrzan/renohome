package com.renohome.service.business;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.HomeServiceRequest;
import com.renohome.domain.repo.HomeServiceRequestRepository;

@RequiredArgsConstructor
@Service
public class HomeServiceRequestService {

    private final HomeServiceRequestRepository homeServiceRequestRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public HomeServiceRequest create(HomeServiceRequest entity) {
        return this.homeServiceRequestRepository.save(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<HomeServiceRequest> findByUuid(UUID uuid) {
        return this.homeServiceRequestRepository.findByUuid(uuid);
    }
}
