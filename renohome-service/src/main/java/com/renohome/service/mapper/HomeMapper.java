package com.renohome.service.mapper;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.domain.entity.Home;
import com.renohome.dto.request.HomeCreateDto;
import com.renohome.dto.response.HomeDto;

@Component
public class HomeMapper {

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<HomeDto> toDto(Home entity) {
        if (entity == null) {
            return Optional.empty();
        }

        HomeDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    private HomeDto buildDto(Home entity) {
        return HomeDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .owner(entity.getOwner())
                .address(entity.getAddress())
                .build();
    }

    public Home toNewEntity(HomeCreateDto request) {
        Objects.requireNonNull(request);

        return Home.builder()
                .owner(request.getOwner())
                .address(request.getAddress())
                .build();
    }

}
