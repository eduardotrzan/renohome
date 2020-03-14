package com.renohome.home.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renohome.home.domain.entity.Service;
import com.renohome.home.domain.entity.enums.ServiceType;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findByUuid(UUID uuid);

    Optional<Service> findByType(ServiceType type);

}
