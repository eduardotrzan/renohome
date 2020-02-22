package com.renohome.service.business;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Contractor;
import com.renohome.domain.repo.ContractorRepository;

@RequiredArgsConstructor
@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Contractor create(Contractor entity) {
        return this.contractorRepository.save(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Contractor> findByUuid(UUID uuid) {
        return this.contractorRepository.findByUuid(uuid);
    }
}
