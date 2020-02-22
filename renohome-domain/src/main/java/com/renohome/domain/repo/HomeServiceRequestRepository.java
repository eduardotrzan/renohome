package com.renohome.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renohome.domain.entity.HomeServiceRequest;

@Repository
public interface HomeServiceRequestRepository extends JpaRepository<HomeServiceRequest, Long> {

    Optional<HomeServiceRequest> findByUuid(UUID uuid);

}
