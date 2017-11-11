package com.rant.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        assertEquals("1 hrs 0 mins 0 secs", Utils.formatTime(3600000l));
        assertEquals("1 mins 0 secs", Utils.formatTime(60000l));
        assertEquals("1 secs", Utils.formatTime(1000l));
        assertEquals("10 ms", Utils.formatTime(10l));

        String time = Utils.formatLong(System.currentTimeMillis());
        assertNotNull(time);

        time = Utils.getDate();
        assertNotNull(time);
    }

    @Test
    public void timeIso8601() {
        Date date = new Date(System.currentTimeMillis());
        String time1 = Utils.formatIso8601Date(date);
        assertNotNull(time1);

        String time2 = Utils.getDateIso8601();
        assertNotNull(time2);
        assertEquals("Null", Utils.formatIso8601Date(null));
    }

    @Test
    public void timeRfc1123() {
        Date date = new Date(System.currentTimeMillis());
        String time1 = Utils.formatRfc1123Date(date);
        assertNotNull(time1);

        String time2 = Utils.getDateRfc1123();
        assertNotNull(time2);
        assertEquals("Null", Utils.formatRfc1123Date(null));
    }

    @Test
    public void formatURLs() {
        assertEquals("http://www.example.com", Utils.formatURL("www.example.com"));
        assertEquals("http://www.example.com", Utils.formatURL("http://www.example.com"));

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
                fail("Expected: " + expected[i] + " but found: " + formatted);
            }
        }
    }

    @Test
    public void formatBytes() {
        double[] numbers = {0d, 5d, 999d, 1024d, 1572864d, 1610612736d, 8342973972.48, 555555d,
                2000000d};
        String[] expected = {"0.00 B", "5.00 B", "999.00 B", "1.00 KB", "1.50 MB", "1.50 GB",
                "7.77 GB", "542.53 KB", "1.91 MB"};
        for (int i = 0; i < numbers.length; i++) {
            double nm = numbers[i];
            String formatted = Utils.formatBytes(nm);
            System.out.println(nm + " => " + formatted);
            if (!formatted.equals(expected[i])) {
                fail("Expected: " + expected[i] + " but found: " + formatted);
            }
        }
    }

    @Test
    public void f() {
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
    public void dates() {

        String td = "June 27 2017";
        java.util.Date date = Utils.convertStringToDate(td);

        assertNotNull(date);
        assertNotNull(Utils.getDate());
        assertNotNull(Utils.formatReadableDate(date));
        assertNotNull(Utils.formatReadableDateTime(date));
        assertNotNull(Utils.formatSQLServerDate(date));
        assertEquals("Null", Utils.formatReadableDate(null));
        assertEquals("Null", Utils.formatReadableDateTime(null));
        assertEquals("Null", Utils.formatSQLServerDate(null));
    }

    @Test
    public void strings() {

        String temp = "  t e st  ";
        assertEquals(Utils.removeAllSpaces(temp), "test");

        temp = "/t@e's?t";
        assertEquals(Utils.removeBadChars(temp), "test");

        temp = "te\r\nst";
        assertEquals(Utils.removeNonAsciiChars(temp), "test");
    }

    @Test
    public void files() {
        try {
            assertNotNull(Utils.getResourceAsFile("/design/testdesign.json"));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            assertNotNull(Utils.downloadUrlFile("http://www.google.com/"));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            Utils.getResourceAsFile(null);
            fail("Failed to catch null file.");
        } catch (Exception e) {
            // good
        }

        try {
            Utils.downloadUrlFile(null);
            fail("Failed to catch null file.");
        } catch (Exception e) {
            // good
        }
    }
}
