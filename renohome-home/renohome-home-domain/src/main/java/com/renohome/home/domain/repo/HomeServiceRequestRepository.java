package com.renohome.home.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renohome.home.domain.entity.HomeServiceRequest;

@Repository
public interface HomeServiceRequestRepository extends JpaRepository<HomeServiceRequest, Long> {

    Optional<HomeServiceRequest> findByUuid(UUID uuid);

}
