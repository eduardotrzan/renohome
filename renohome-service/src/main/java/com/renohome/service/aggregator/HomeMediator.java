package com.renohome.service.aggregator;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Home;
import com.renohome.dto.request.HomeCreateDto;
import com.renohome.dto.response.HomeDto;
import com.renohome.service.business.HomeService;
import com.renohome.service.mapper.HomeMapper;
import com.renohome.service.validation.exception.NotFoundException;

@RequiredArgsConstructor
@Component
public class HomeMediator {

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
