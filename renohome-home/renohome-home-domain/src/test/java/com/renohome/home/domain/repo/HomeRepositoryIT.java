package com.renohome.home.domain.repo;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.renohome.home.domain.entity.Home;
import com.renohome.home.domain.testutils.AbstractRenohomeDomainRepoIT;

@Slf4j
public class HomeRepositoryIT extends AbstractRenohomeDomainRepoIT {

    @Test
    public void saveHome() {
        Home home = super.getRenoHomeDatabasePrefillUtils()
                .withRandomHome()
                .saveAndGet()
                .getHome();
        log.info("home={}", home);

        assertThat(home).isNotNull();
        assertThat(home.getId())
                .isNotNull()
                .isPositive();
        assertThat(home.getAddress())
                .isNotBlank();
        assertThat(home.getOwner())
                .isNotBlank();
    }

}
