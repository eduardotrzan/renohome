package com.renohome.home.domain.testutils;

import lombok.Getter;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { RenoHomeDomainRepoITConfig.class })
@ActiveProfiles("renohome-junit")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractRenohomeDomainRepoIT {

    @Getter
    @Autowired
    private RenoHomeDatabasePrefillUtils renoHomeDatabasePrefillUtils;

}
