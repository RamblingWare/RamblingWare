package com.rw.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rw.config.Utils;

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

    @Test
    public void formatLong() {

        long[] numbers = {0, 5, 999, 1_000, -5_821, 10_500, -101_800, 2_000_000, -7_800_000,
                92_150_000, 123_200_000, 9_999_999, 999_999_999_999_999_999L,
                1_230_000_000_000_000L, Long.MIN_VALUE, Long.MAX_VALUE};
        String[] expected = {"0", "5", "999", "1k", "-5.8k", "10k", "-101k", "2M", "-7.8M", "92M",
                "123M", "9.9M", "999P", "1.2P", "-9.2E", "9.2E"};
        for (int i = 0; i < numbers.length; i++) {
            long n = numbers[i];
            String formatted = Utils.formatLong(n);
            System.out.println(n + " => " + formatted);
            if (!formatted.equals(expected[i]))
                throw new AssertionError("Expected: " + expected[i] + " but found: " + formatted);
        }

    }
    
    @Test
    public void emailFormat() {
        assertTrue(Utils.isValidEmail("jdoe@gmail.com"));
        assertTrue(Utils.isValidEmail("jdoe@yahoo.com"));
        assertTrue(Utils.isValidEmail("jdoe@us.example.com"));
        assertTrue(Utils.isValidEmail("j.doe@gmail.com"));
        
        assertTrue(!Utils.isValidEmail("jdoe@@gmail.com"));
        assertTrue(!Utils.isValidEmail("@gmail.com"));
        assertTrue(!Utils.isValidEmail("jdoe@"));
        assertTrue(!Utils.isValidEmail("jdoe@gmail."));
        assertTrue(!Utils.isValidEmail(""));
    }
}
