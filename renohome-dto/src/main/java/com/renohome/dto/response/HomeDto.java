package com.renohome.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid", "createDate", "updateDate", "owner", "address" })
public class HomeDto {

    private UUID uuid;

    private OffsetDateTime createDate;

    private OffsetDateTime updateDate;

    private String owner;

    private String address;

}
