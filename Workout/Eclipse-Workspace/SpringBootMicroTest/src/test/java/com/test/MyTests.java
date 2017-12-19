package com.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.test.service.TestService;

public class MyTests {

    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
        TestService tester = new TestService();
        
        // assert statements
        assertEquals(4, tester.multiply(2, 2));
        assertEquals(9, tester.multiply(3, 3));
        assertEquals(16, tester.multiply(4, 4));
    }
    
}