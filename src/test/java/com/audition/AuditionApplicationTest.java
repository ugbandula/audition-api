package com.audition;

import com.audition.web.AuditionController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuditionApplicationTest {

    // TODO implement unit test. Note that an applicant should create additional unit tests as required.
    @Autowired
    private AuditionController controller;

    @Test
    private void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

}
