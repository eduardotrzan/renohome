package com.renohome.home.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.renohome.generic.domain.entity.AbstractEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "home_service_request")
@ToString(callSuper = true, of = { "id", "budget" })
public class HomeServiceRequest extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_service_request_id_seq")
    @SequenceGenerator(name = "home_service_request_id_seq", sequenceName = "home_service_request_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, name = "schedule_date")
    private OffsetDateTime scheduleDate;

    @ManyToOne(targetEntity = Service.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;

    @ManyToOne(targetEntity = Service.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "contractor_uuid")
    private UUID contractorUuid;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;
}
