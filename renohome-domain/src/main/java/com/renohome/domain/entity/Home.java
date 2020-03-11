package com.renohome.domain.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

import com.renohome.generic.domain.entity.AbstractEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "home")
@ToString(callSuper = true, of = { "id" })
public class Home extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_id_seq")
    @SequenceGenerator(name = "home_id_seq", sequenceName = "home_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "owner", nullable = false, length = 200)
    private String owner;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY)
    private List<HomeServiceRequest> homeServiceRequests;
}
