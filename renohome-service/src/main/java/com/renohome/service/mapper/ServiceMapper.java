package com.renohome.service.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Service;
import com.renohome.dto.enums.ServiceTypeDto;
import com.renohome.dto.response.ServiceDto;

@Component
public class ServiceMapper {

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<ServiceDto> toDto(Service entity) {
        if (entity == null) {
            return Optional.empty();
        }

        ServiceDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    private ServiceDto buildDto(Service entity) {
        ServiceTypeDto type = entity.getType() == null
                ? null
                : ServiceTypeDto.valueOf(entity.getType().name());
        return ServiceDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .type(type)
                .build();
    }
}
