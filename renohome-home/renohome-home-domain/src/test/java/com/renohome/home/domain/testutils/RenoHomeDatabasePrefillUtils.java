package com.renohome.home.domain.testutils;

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
import com.renohome.home.domain.entity.Home;
import com.renohome.home.domain.entity.HomeServiceRequest;
import com.renohome.home.domain.entity.Service;
import com.renohome.home.domain.entity.enums.ServiceType;
import com.renohome.home.domain.repo.HomeRepository;
import com.renohome.home.domain.repo.HomeServiceRequestRepository;
import com.renohome.home.domain.repo.ServiceRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class RenoHomeDatabasePrefillUtils {

    private final HomeRepository homeRepository;

    private final HomeServiceRequestRepository homeServiceRequestRepository;

    private final ServiceRepository serviceRepository;

    private Home home;

    private HomeServiceRequest homeServiceRequest;

    private Service service;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabasePrefillContext {

        private Home home;

        private HomeServiceRequest homeServiceRequest;

        private Service service;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DatabasePrefillContext saveAndGet() {
        doSave();
        DatabasePrefillContext databasePrefillContext = DatabasePrefillContext.builder()
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
        this.saveHome();
        this.saveService();
        this.saveHomeServiceRequest();
    }

    private void flush() {
        this.home = null;
        this.homeServiceRequest = null;
        this.service = null;
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

        this.homeServiceRequest.setHome(this.home);
        this.homeServiceRequest.setService(this.service);

        if (this.home.getHomeServiceRequests() == null || this.home.getHomeServiceRequests().isEmpty()) {
            this.home.setHomeServiceRequests(new ArrayList<>());
        }
        this.home.getHomeServiceRequests().add(this.homeServiceRequest);


        this.homeServiceRequest = this.homeServiceRequestRepository.save(this.homeServiceRequest);
        log.info("Saved homeServiceRequest={}.", this.homeServiceRequest);
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
//                .budget(this.randomDecimalBetween(150, 1500))
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
