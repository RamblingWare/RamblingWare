package com.rant.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rant.config.Utils;

/**
 * Unit tests for Utils
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class UtilsTests {

    @Test
    public void time() {

        String time = Utils.formatTime(System.currentTimeMillis());
        assertNotNull(time);
        
        time = Utils.formatLong(System.currentTimeMillis());
        assertNotNull(time);
    }
    
    @Test
    public void formatURLs() {        
        assertEquals("http://www.example.com", Utils.formatURL("www.example.com"));
        
        assertEquals("TL;DR", Utils.formatURI("/\\TL;DR/"));
        assertEquals("test-one-test", Utils.formatURI("test one test"));
        assertEquals("Question", Utils.formatURI("Question?!"));
        assertEquals("a-bnc-d", Utils.formatURI("a=b&c=d"));
        assertEquals("array,array", Utils.formatURI("[array,array]"));
    }

    @Test
    public void formatLong() {

        long[] numbers = {0, 5, 999, 1_000, -5_821, 10_500, -101_800, 2_000_000, -7_800_000,
                92_150_000, 123_200_000, 9_999_999, 999_999_999_999_999_999L,
                1_230_000_000_000_000L, Long.MIN_VALUE, Long.MAX_VALUE};
        String[] expected = {"0", "5", "999", "1k", "-5.8k", "10k", "-101k", "2M", "-7.8M", "92M",
                "123M", "9.9M", "999P", "1.2P", "-9.2E", "9.2E"};
        for (int i = 0; i < numbers.length; i++) {
            long nm = numbers[i];
            String formatted = Utils.formatLong(nm);
            System.out.println(nm + " => " + formatted);
            if (!formatted.equals(expected[i])) {
                throw new AssertionError("Expected: " + expected[i] + " but found: " + formatted);
            }
        }

    }

    @Test
    public void emailFormat() {
        assertTrue(Utils.isValidEmail("jdoe1@example.com"));
        assertTrue(Utils.isValidEmail("jdoe1@example.com"));
        assertTrue(Utils.isValidEmail("jdoe1@example.example.com"));
        assertTrue(Utils.isValidEmail("j.doe1@example.com"));

        assertFalse(Utils.isValidEmail("jdoe1@@example.com"));
        assertFalse(Utils.isValidEmail("@gmail.com"));
        assertFalse(Utils.isValidEmail("jdoe1@"));
        assertFalse(Utils.isValidEmail("jdoe1@example."));
        assertFalse(Utils.isValidEmail(""));
    }

    @Test
    public void dateTests() {

        String td = "June 27 2017";
        java.util.Date date = Utils.convertStringToDate(td);
        
        assertNotNull(date);
        assertNotNull(Utils.getDate());
        assertNotNull(Utils.formatReadableDate(date));
        assertNotNull(Utils.formatReadableDateTime(date));
        assertNotNull(Utils.formatSQLServerDate(date));        
    }
    
    @Test
    public void stringTests() {
        
        String temp = "  t e st  ";
        assertEquals(Utils.removeAllSpaces(temp),"test");
        
        temp = "/t@e's?t";
        assertEquals(Utils.removeBadChars(temp),"test");
        
        temp = "te\r\nst";
        assertEquals(Utils.removeNonAsciiChars(temp),"test");
    }
    
    @Test public void fileLoading() {
        
        assertNotNull(Utils.getResourceAsFile("/design/testdesign.json"));
        
        assertNotNull(Utils.downloadUrlFile("http://localhost:5984/"));
    }
}
