package org.oddox.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for Utils
 */
@RunWith(JUnit4.class)
public class UtilsTests {

    @InjectMocks
    private Utils utils;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(utils != null);
    }

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
        assertEquals("https://www.example.com", Utils.formatURL("https://www.example.com"));

        assertEquals("TL;DR", Utils.formatURI("/\\TL;DR/"));
        assertEquals("test-one-test", Utils.formatURI("test one test"));
        assertEquals("Question", Utils.formatURI("Question?!"));
        assertEquals("a-bnc-d", Utils.formatURI("a=b&c=d"));
        assertEquals("array,array", Utils.formatURI("[array,array]"));
        
        assertEquals("https://127.0.0.1:6984/", Utils.removeUserPassFromURL("https://admin:p4ssw0rd@127.0.0.1:6984/"));
        assertEquals("https://127.0.0.1:6984/", Utils.removeUserPassFromURL("https://127.0.0.1:6984/"));
        assertEquals("http://localhost:5984/", Utils.removeUserPassFromURL("http://admin:admin@localhost:5984/"));
    }

    @Test
    public void formatLong() {

        long[] numbers = { 0, 5, 999, 1_000, -5_821, 10_500, -101_800, 2_000_000, -7_800_000, 92_150_000, 123_200_000,
                9_999_999, 999_999_999_999_999_999L, 1_230_000_000_000_000L, Long.MIN_VALUE, Long.MAX_VALUE };
        String[] expected = { "0", "5", "999", "1k", "-5.8k", "10k", "-101k", "2M", "-7.8M", "92M", "123M", "9.9M",
                "999P", "1.2P", "-9.2E", "9.2E" };
        for (int i = 0; i < numbers.length; i++) {
            long nm = numbers[i];
            String formatted = Utils.formatLong(nm);
            if (!formatted.equals(expected[i])) {
                fail("Expected: " + expected[i] + " but found: " + formatted);
            }
        }
    }

    @Test
    public void formatBytes() {
        double[] numbers = { 0d, 5d, 999d, 1024d, 1572864d, 1610612736d, 8342973972.48, 555555d, 2000000d };
        String[] expected = { "0.00 B", "5.00 B", "999.00 B", "1.00 KB", "1.50 MB", "1.50 GB", "7.77 GB", "542.53 KB",
                "1.91 MB" };
        for (int i = 0; i < numbers.length; i++) {
            double nm = numbers[i];
            String formatted = Utils.formatBytes(nm);
            if (!formatted.equals(expected[i])) {
                fail("Expected: " + expected[i] + " but found: " + formatted);
            }
        }
    }

    @Test
    public void formatEmails() {
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
        Date date = Utils.convertStringToDate("June 22, 1988");

        assertNotNull(date);
        assertNotNull(Utils.getDate());
        assertNotNull(Utils.formatReadableDate(date));
        assertNotNull(Utils.formatReadableDateTime(date));
        assertNotNull(Utils.formatSQLServerDate(date));
        assertEquals("Null", Utils.formatReadableDate(null));
        assertEquals("Null", Utils.formatReadableDateTime(null));
        assertEquals("Null", Utils.formatSQLServerDate(null));

        assertNull(Utils.convertStringToDate(null));
    }

    @Test
    public void strings() {
        String temp = "  t e st  ";
        assertEquals("test", Utils.removeAllSpaces(temp));

        temp = "/t@e's?t";
        assertEquals("test", Utils.removeBadChars(temp));

        temp = "te\r\nst";
        assertEquals("test", Utils.removeNonAsciiChars(temp));
    }

    @Test
    public void designFiles() {
        try {
            assertNotNull(Utils.getResourceAsFile("/design/testdesign.json"));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            assertNotNull(Utils.downloadUrlFile("http://oddox.org/"));
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

        try {
            Utils.getResourceAsFile("");
            fail("Failed to catch empty file.");
        } catch (Exception e) {
            // good
        }

        try {
            Utils.downloadUrlFile("");
            fail("Failed to catch empty file.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void propertiesFiles() {
        try {
            HashMap<String, String> map = Utils.loadMapFromFile("/app-test.properties");
            assertNotNull(map);
            assertEquals("Oddox", map.get("name"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            HashMap<String, String> map = Utils.loadMapFromFile("/db-test.properties");
            assertEquals("127.0.0.1", map.get("couchdb.host"));
            assertNotNull(map);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            HashMap<String, String> map = Utils.loadMapFromFile("/bad-file-name.properties");
            assertNull(map);
        } catch (Exception e) {
            // good
        }

        try {
            HashMap<String, String> map = Utils.loadMapFromFile(null);
            assertNull(map);
        } catch (Exception e) {
            // good
        }

        try {
            HashMap<String, String> map = Utils.loadMapFromFile("");
            assertNull(map);
        } catch (Exception e) {
            // good
        }
    }

}
