package com.renohome.contractor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.renohome.contractor.dto.enums.ContractorServiceTypeDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name", "phone", "cost", "serviceType" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractorCreateDto {

    private String name;

    private String phone;

    private BigDecimal cost;

    private ContractorServiceTypeDto serviceType;
}
