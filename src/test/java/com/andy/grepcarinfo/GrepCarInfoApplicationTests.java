package com.andy.grepcarinfo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "mariadb"})
class GrepCarInfoApplicationTests {

    @Test
    void contextLoads() {
    }

}
