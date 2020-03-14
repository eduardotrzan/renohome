package com.renohome.home.service.business.internal;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.home.domain.entity.Home;
import com.renohome.home.domain.repo.HomeRepository;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final HomeRepository homeRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Home create(Home entity) {
        return this.homeRepository.save(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Home> findByUuid(UUID uuid) {
        return this.homeRepository.findByUuid(uuid);
    }
}
