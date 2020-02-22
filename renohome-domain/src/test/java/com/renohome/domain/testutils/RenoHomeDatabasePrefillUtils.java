package com.renohome.domain.testutils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Address;
import com.devskiller.jfairy.producer.person.Person;
import com.renohome.domain.entity.Contractor;
import com.renohome.domain.entity.Home;
import com.renohome.domain.entity.HomeServiceRequest;
import com.renohome.domain.entity.Service;
import com.renohome.domain.entity.enums.ServiceType;
import com.renohome.domain.repo.ContractorRepository;
import com.renohome.domain.repo.HomeRepository;
import com.renohome.domain.repo.HomeServiceRequestRepository;
import com.renohome.domain.repo.ServiceRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class RenoHomeDatabasePrefillUtils {

    private final ContractorRepository contractorRepository;

    private final HomeRepository homeRepository;

    private final HomeServiceRequestRepository homeServiceRequestRepository;

    private final ServiceRepository serviceRepository;

    private Contractor contractor;

    private Home home;

    private HomeServiceRequest homeServiceRequest;

    private Service service;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabasePrefillContext {

        private Contractor contractor;

        private Home home;

        private HomeServiceRequest homeServiceRequest;

        private Service service;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DatabasePrefillContext saveAndGet() {
        doSave();
        DatabasePrefillContext databasePrefillContext = DatabasePrefillContext.builder()
                .contractor(this.contractor)
                .home(this.home)
                .homeServiceRequest(this.homeServiceRequest)
                .service(this.service)
                .build();
        flush();
        return databasePrefillContext;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save() {
        doSave();
        flush();
    }

    private void doSave() {
        this.saveContractor();
        this.saveHome();
        this.saveService();
        this.saveHomeServiceRequest();
    }

    private void flush() {
        this.contractor = null;
        this.home = null;
        this.homeServiceRequest = null;
        this.service = null;
    }

    private void saveContractor() {
        log.info("Saving contractor...");
        if (this.contractor == null) {
            log.info("contractor is null, skipping save.");
            return;
        }

        this.contractor = this.contractorRepository.save(this.contractor);
        log.info("Saved contractor={}.", this.contractor);
    }

    private void saveHome() {
        log.info("Saving home...");
        if (this.home == null) {
            log.info("home is null, skipping save.");
            return;
        }
        this.home = this.homeRepository.save(this.home);
        log.info("Saved home={}.", this.home);
    }

    private void saveService() {
        log.info("Saving service...");
        if (this.service == null) {
            log.info("service is null, skipping save.");
            return;
        }
        this.service = this.serviceRepository.save(this.service);
        log.info("service home={}.", this.service);
    }

    private void saveHomeServiceRequest() {
        log.info("Saving homeServiceRequest...");
        if (this.contractor == null) {
            log.info("contractor is null, skipping save.");
            return;
        }

        if (this.home == null) {
            log.info("home is null, skipping save.");
            return;
        }

        if (this.service == null) {
            log.info("Garage is null, skipping save.");
            return;
        }

        if (this.homeServiceRequest == null) {
            log.info("homeServiceRequest is null, skipping save.");
            return;
        }

        this.homeServiceRequest.setContractor(this.contractor);
        this.homeServiceRequest.setHome(this.home);
        this.homeServiceRequest.setService(this.service);

        if (this.home.getHomeServiceRequests() == null || this.home.getHomeServiceRequests().isEmpty()) {
            this.home.setHomeServiceRequests(new ArrayList<>());
        }
        this.home.getHomeServiceRequests().add(this.homeServiceRequest);


        if (this.contractor.getHomeServiceRequests() == null || this.contractor.getHomeServiceRequests().isEmpty()) {
            this.contractor.setHomeServiceRequests(new ArrayList<>());
        }
        this.contractor.getHomeServiceRequests().add(this.homeServiceRequest);

        this.homeServiceRequest = this.homeServiceRequestRepository.save(this.homeServiceRequest);
        log.info("Saved homeServiceRequest={}.", this.homeServiceRequest);
    }

    public RenoHomeDatabasePrefillUtils withRandomContractor() {
        Person person = Fairy.create().person();
        this.contractor = Contractor.builder()
                .name(person.getFullName())
                .phone(person.getPhone())
                .cost(this.randomDecimalBetween(400, 2000))
                .build();
        return this;
    }

    public RenoHomeDatabasePrefillUtils withRandomHome() {
        Person person = Fairy.create().person();
        this.home = Home.builder()
                .owner(person.getFirstName())
                .address(this.toSingleLine(person.getAddress()))
                .build();
        return this;
    }

    private String toSingleLine(Address address) {
        return new StringBuilder()
                .append(address.getApartmentNumber())
                .append("-")
                .append(address.getStreetNumber())
                .append(" ")
                .append(address.getStreet())
                .append(", ")
                .append(address.getCity())
                .append(" ")
                .append(address.getPostalCode())
                .toString();

    }

    public RenoHomeDatabasePrefillUtils withRandomService() {
        List<ServiceType> serviceTypes = new ArrayList<>(EnumSet.allOf(ServiceType.class));
        ServiceType randomServiceType = serviceTypes.get(this.randomIntegerBetween(0, serviceTypes.size() -1));
        this.service = Service.builder()
                .type(randomServiceType)
                .budget(this.randomDecimalBetween(150, 1500))
                .build();
        return this;
    }

    public RenoHomeDatabasePrefillUtils withRandomHomeServiceRequest() {
        this.homeServiceRequest = HomeServiceRequest.builder()
                .scheduleDate(OffsetDateTime.now().plusDays(this.randomIntegerBetween(0, 50)))
                .build();
        return this;
    }

    private int randomIntegerBetween(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        int bound = (max - min) + 1;
        return new Random().nextInt(bound) + min;
    }

    private BigDecimal randomDecimalBetween(double minDouble, double maxDouble) {
        BigDecimal min = BigDecimal.valueOf(minDouble);
        BigDecimal max = BigDecimal.valueOf(maxDouble);

        BigDecimal randomFactor = BigDecimal.valueOf(Math.random());
        BigDecimal randomBigDecimal = min.add(randomFactor.multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_DOWN);
    }
}
