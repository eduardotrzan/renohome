package com.renohome.service.mapper;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.HomeServiceRequest;
import com.renohome.dto.request.HomeServiceRequestCreateDto;
import com.renohome.dto.response.ContractorDto;
import com.renohome.dto.response.HomeDto;
import com.renohome.dto.response.HomeServiceRequestDto;
import com.renohome.dto.response.ServiceDto;

@RequiredArgsConstructor
@Component
public class HomeServiceRequestMapper {

    private final ContractorMapper contractorMapper;

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

        ContractorDto contractor = this.contractorMapper.toDto(entity.getContractor())
                .orElse(null);

        return HomeServiceRequestDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .scheduleDate(entity.getScheduleDate())
                .budget(entity.getBudget())
                .home(home)
                .service(service)
                .contractor(contractor)
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
