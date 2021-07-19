package com.revature;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Practice file for getting tests to run
 */

@SpringBootTest
public class JUnitControllerTest {

    @Test
    public void testHomeController() {
        
        String result = "Hello World!";
        assertEquals(result, "Hello World!");
    }
}
