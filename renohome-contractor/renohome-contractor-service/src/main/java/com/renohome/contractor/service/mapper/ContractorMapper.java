package com.renohome.contractor.service.mapper;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renohome.contractor.domain.entity.Contractor;
import com.renohome.contractor.dto.request.ContractorCreateDto;
import com.renohome.contractor.dto.response.ContractorDto;

@Component
public class ContractorMapper {

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<ContractorDto> toDto(Contractor entity) {
        if (entity == null) {
            return Optional.empty();
        }

        ContractorDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    private ContractorDto buildDto(Contractor entity) {
        return ContractorDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .name(entity.getName())
                .phone(entity.getPhone())
                .cost(entity.getCost())
                .build();
    }

    public Contractor toNewEntity(ContractorCreateDto request) {
        Objects.requireNonNull(request);

        return Contractor.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .cost(request.getCost())
                .build();
    }

}
