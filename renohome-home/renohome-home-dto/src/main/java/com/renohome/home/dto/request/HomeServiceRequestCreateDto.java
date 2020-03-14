package com.renohome.home.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.renohome.home.dto.enums.ServiceTypeDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "serviceType", "scheduleDate", "budget" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeServiceRequestCreateDto {

    private ServiceTypeDto serviceType;

    private OffsetDateTime scheduleDate;

    private BigDecimal budget;
}
