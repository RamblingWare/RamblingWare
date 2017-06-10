package com.rw.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rw.config.Utils;

/**
 * Unit tests for Utils
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class UtilsTests {

    @Test
    public void test() {
        
        String time = Utils.formatTime(System.currentTimeMillis());
        
        assertNotNull(time);
    }
}
