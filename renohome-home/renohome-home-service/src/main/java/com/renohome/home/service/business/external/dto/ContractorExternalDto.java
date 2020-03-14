package com.renohome.home.service.business.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractorExternalDto {

    private UUID uuid;

    private BigDecimal cost;

}
