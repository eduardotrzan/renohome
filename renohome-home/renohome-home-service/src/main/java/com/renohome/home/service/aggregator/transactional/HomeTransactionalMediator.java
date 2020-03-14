package com.renohome.home.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.generic.expcetions.NotFoundException;
import com.renohome.home.domain.entity.Home;
import com.renohome.home.dto.request.HomeCreateDto;
import com.renohome.home.dto.response.HomeDto;
import com.renohome.home.service.business.internal.HomeService;
import com.renohome.home.service.mapper.HomeMapper;

@RequiredArgsConstructor
@Component
public class HomeTransactionalMediator {

    private final HomeService homeService;

    private final HomeMapper homeMapper;

    @Transactional
    public HomeDto create(HomeCreateDto request) {
        Home entity = this.homeMapper.toNewEntity(request);
        Home savedEntity = this.homeService.create(entity);

        return this.homeMapper
                .toDto(savedEntity)
                .orElseThrow(() -> new NotFoundException(Home.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public Optional<HomeDto> findByUuid(UUID uuid) {
        return this.homeService
                .findByUuid(uuid)
                .flatMap(homeMapper::toDto);
    }
}
