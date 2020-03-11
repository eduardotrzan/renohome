package com.renohome.contractor.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renohome.contractor.domain.entity.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    Optional<Contractor> findByUuid(UUID uuid);

}
