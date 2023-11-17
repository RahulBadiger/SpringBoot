package com.spring.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class DemoApplicationTest {

    @Test
    void testMain() {
        DemoApplication.main(new String[] {});
        assertTrue(true);
    }
}