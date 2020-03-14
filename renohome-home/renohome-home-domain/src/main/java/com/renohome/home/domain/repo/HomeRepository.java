package com.renohome.home.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renohome.home.domain.entity.Home;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

    Optional<Home> findByUuid(UUID uuid);

}
