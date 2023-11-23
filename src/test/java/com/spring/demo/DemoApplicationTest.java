package com.spring.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DemoApplicationTest {

    @Test
    void testMain() {
        DemoApplication.main(new String[]{});
        assertTrue(true);
    }
}