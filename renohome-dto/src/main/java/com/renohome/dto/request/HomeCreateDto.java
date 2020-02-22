package com.renohome.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "owner", "address" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeCreateDto {

    private String owner;

    private String address;
}
