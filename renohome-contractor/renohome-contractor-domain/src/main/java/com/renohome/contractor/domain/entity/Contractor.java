package com.renohome.contractor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

import com.renohome.contractor.domain.entity.enums.ContractorServiceType;
import com.renohome.generic.domain.entity.AbstractEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contractor")
@ToString(callSuper = true, of = { "id" })
public class Contractor extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractor_id_seq")
    @SequenceGenerator(name = "contractor_id_seq", sequenceName = "contractor_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "phone", nullable = false, length = 200)
    private String phone;

    @Column(name = "cost", nullable = false, precision = 24, scale = 12)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 200)
    private ContractorServiceType serviceType;
}
