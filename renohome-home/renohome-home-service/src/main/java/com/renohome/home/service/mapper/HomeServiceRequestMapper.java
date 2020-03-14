package com.renohome.home.service.mapper;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.home.domain.entity.HomeServiceRequest;
import com.renohome.home.dto.request.HomeServiceRequestCreateDto;
import com.renohome.home.dto.response.HomeDto;
import com.renohome.home.dto.response.HomeServiceRequestDto;
import com.renohome.home.dto.response.ServiceDto;

@RequiredArgsConstructor
@Component
public class HomeServiceRequestMapper {

    private final HomeMapper homeMapper;

    private final ServiceMapper serviceMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<HomeServiceRequestDto> toDto(HomeServiceRequest entity) {
        if (entity == null) {
            return Optional.empty();
        }

        HomeServiceRequestDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    private HomeServiceRequestDto buildDto(HomeServiceRequest entity) {
        HomeDto home = this.homeMapper.toDto(entity.getHome())
                .orElse(null);

        ServiceDto service = this.serviceMapper.toDto(entity.getService())
                .orElse(null);

        return HomeServiceRequestDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .scheduleDate(entity.getScheduleDate())
                .budget(entity.getBudget())
                .home(home)
                .service(service)
                .build();
    }

    public HomeServiceRequest toNewEntity(HomeServiceRequestCreateDto request) {
        Objects.requireNonNull(request);

        return HomeServiceRequest.builder()
                .scheduleDate(request.getScheduleDate())
                .budget(request.getBudget())
                .build();
    }

}
