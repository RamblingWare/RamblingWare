package com.rant.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * A simple utility class that contains common methods used across classes.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Utils {

    private static final DecimalFormat BYTEFORM = new DecimalFormat("0.00");
    private static final DateFormat READABLEDATEFORM = new SimpleDateFormat("MMM dd, yyyy");
    private static final DateFormat READABLEDATETIMEFORM = new SimpleDateFormat(
            "MMM dd, yyyy (h:mm a z)");
    private static final DateFormat SQLSERVERDATEFORM = new SimpleDateFormat("yyyyMMdd hh:mm:ss a");
    private static final DateFormat ISO8601FORM = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateFormat RFC1123FORM = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    static {
        ISO8601FORM.setTimeZone(TimeZone.getTimeZone("UTC"));
        RFC1123FORM.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Gets the current time in a readable format. "MMM dd, yyyy (h:mm a z)"
     * 
     * @return String
     */
    public static String getDate() {
        return formatReadableDate(new Date(System.currentTimeMillis()));
    }

    /**
     * Gets the current time in ISO 8601 format. "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * 
     * @return String
     */
    public static String getDateIso8601() {
        return formatIso8601Date(new Date(System.currentTimeMillis()));
    }

    /**
     * Gets the current time in RFC 1123 format. "EEE, dd MMM yyyy HH:mm:ss z"
     * 
     * @return String
     */
    public static String getDateRfc1123() {
        return formatRfc1123Date(new Date(System.currentTimeMillis()));
    }

    /**
     * Format bytes to a readable unit of measure. B, KB, MB, GB, etc...
     * 
     * @param bytes
     *            double number
     * @return String
     */
    public static String formatBytes(double bytes) {
        if (bytes >= 1073741824.0) {
            return BYTEFORM.format(bytes / 1073741824.0) + " GB"; // gigabytes
        } else if (bytes >= 1048576) {
            return BYTEFORM.format(bytes / 1048576.0) + " MB"; // megabytes
        } else if (bytes >= 1024) {
            return BYTEFORM.format(bytes / 1024.0) + " KB"; // kilobytes
        } else {
            return BYTEFORM.format(bytes) + " B"; // bytes
        }
    }

    /**
     * Formats the time in milliseconds given to a readable unite of measure. X hrs X mins X secs X
     * ms
     * 
     * @param timeInMilliseconds
     *            time in millis (long)
     * @return String
     */
    public static String formatTime(long timeInMilliseconds) {
        if (timeInMilliseconds >= 3600000) {
            return (timeInMilliseconds / 3600000) + " hrs " + (timeInMilliseconds / 60000 % 60)
                    + " mins " + (timeInMilliseconds / 1000 % 60) + " secs";
        } else if (timeInMilliseconds >= 60000) {
            return (timeInMilliseconds / 60000 % 60) + " mins " + (timeInMilliseconds / 1000 % 60)
                    + " secs";
        } else if (timeInMilliseconds >= 1000) {
            return (timeInMilliseconds / 1000 % 60) + " secs";
        } else {
            return timeInMilliseconds + " ms";
        }
    }

    /**
     * Get a readable format of the given date. "MMM dd, yyyy"
     * 
     * @param date
     *            "MMM dd, yyyy"
     * @return String
     */
    public static String formatReadableDate(Date date) {
        if (date == null) {
            return "Null";
        }
        return READABLEDATEFORM.format(date);
    }

    /**
     * Get a readable format of the given date. "MMM dd, yyyy (hh:mm a z)"
     * 
     * @param date
     *            "MMM dd, yyyy (hh:mm a z)"
     * @return String
     */
    public static String formatReadableDateTime(Date date) {
        if (date == null) {
            return "Null";
        }
        return READABLEDATETIMEFORM.format(date);
    }

    /**
     * Get a SQLServer database format of the given datetime. "yyyyMMdd hh:mm:ss a"
     * 
     * @param dateTime
     *            "yyyyMMdd hh:mm:ss a"
     * @return String
     */
    public static String formatSQLServerDate(Date dateTime) {
        if (dateTime == null) {
            return "Null";
        }
        return SQLSERVERDATEFORM.format(dateTime);
    }

    /**
     * Get a ISO 8601 format of the given datetime. "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * 
     * @param dateTime
     *            java.util.Date
     * @return String "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String formatIso8601Date(Date dateTime) {
        if (dateTime == null) {
            return "Null";
        }
        return ISO8601FORM.format(dateTime);
    }

    /**
     * Get a RFC 1123 format of the given datetime. "EEE, dd MMM yyyy HH:mm:ss z"
     * 
     * @param dateTime
     *            java.util.Date
     * @return String "EEE, dd MMM yyyy HH:mm:ss z"
     */
    public static String formatRfc1123Date(Date dateTime) {
        if (dateTime == null) {
            return "Null";
        }
        return RFC1123FORM.format(dateTime);
    }

    /**
     * Tries to parse the String in any format, and convert it into a util.Date object. If it cannot
     * figure out what Date the String represents, then it will return null.
     * 
     * @param anyFormat
     *            "MM/dd/yy", "MMMM d yy", "MMM dd yyyy"...
     * @return Date
     */
    public static Date convertStringToDate(String anyFormat) {
        String[] formats = {"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyyMMdd hh:mm:ss a",
                "yyyyMMdd hh:mm:ss", "yyyy-MM-dd hh:mm:ss a", "MM/dd/yy", "MMMM d yy",
                "MMM dd yyyy", "MMM dd yy", "MMMM d, yy", "MM/dd/yyyy", "dd/MM/yy", "dd/MM/yyyy",
                "yyyy-MM-dd", "EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm:ss",
                "dd MMM yy HH:mm:ss", "ss:mm:HH dd MM:"};
        Date date = null;

        for (int i = 0; i < formats.length; i++) {
            try {
                date = new SimpleDateFormat(formats[i], Locale.ENGLISH).parse(anyFormat);
                /*
                 * System.out.println("Format found: \"" + formats[i] + "\" for date input \"" +
                 * anyFormat + "\"");
                 */
                break; // successful
            } catch (Exception e) {
                continue; // try another
            }
        }

        return date;
    }

    /**
     * Strips out all spaces, newlines, returns, and tabs.
     * 
     * @param text
     *            to clean
     * @return String
     */
    public static String removeAllSpaces(String text) {
        return text.replaceAll("\\s", "");
    }

    /**
     * Strips out bad text characters from the given string.
     * 
     * @param text
     *            to clean
     * @return String
     */
    public static String removeBadChars(String text) {
        text = text.replaceAll("[\\^/?<>\\:*`'~!\\\\.,;@#$%()\\[\\]+{}\"]", "").trim();
        return text.replaceAll("\\s+", " ");
    }

    /**
     * Strips out all Non-ASCII characters from the given string.
     * 
     * @param text
     *            to clean
     * @return String
     */
    public static String removeNonAsciiChars(String text) {
        // Remove all non-ASCII, non-printable characters
        // "\p{Print}" represents a POSIX character
        // "\P{Print}" represents the complement of "\p{Print}"
        return text.replaceAll("\\P{Print}", "");
    }

    /**
     * Make sure the URL starts with 'http://' or 'https://'
     * 
     * @param url
     *            URL string
     * @return String
     */
    public static String formatURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "http://" + url;
        } else {
            return url;
        }
    }

    /**
     * Make sure the URI is compliant for context. Does not work on UrlParams.
     * 
     * @param uri
     *            URI string
     * @return String
     */
    public static String formatURI(String uri) {
        uri = uri.replaceAll("[\\^/?<>\\.#*`'~!\\\\\\[\\]+{}\"]", "").trim();
        uri = uri.replaceAll("[=\\s+\"]", "-");
        uri = uri.replace(" ", "-");
        uri = uri.replace("&", "n");
        uri = uri.replace("=", "n");
        return uri;
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    /**
     * Get a nice label for large numbers, e.g. 100, 1.3k, 12.5B, 500k, 1.1M...
     * 
     * @param value
     *            long
     * @return String
     */
    public static String formatLong(long value) {
        // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) {
            return formatLong(Long.MIN_VALUE + 1);
        } else if (value < 0) {
            return "-" + formatLong(-value);
        } else if (value < 1000) {
            return Long.toString(value); // deal with easy case
        }

        Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); // the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    /**
     * Checks if the email address given is a valid form of an Internet address.
     * <ul>
     * <li>Must have one @ symbol</li>
     * <li>Must have domain name like domain.com</li>
     * <li>Must not have illegal characters</li>
     * </ul>
     * 
     * @param email
     *            string
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Downloads the urlString to the filename at the current directory.
     * 
     * @param urlString
     *            URL string
     * @return String
     * @throws IOException
     *             if error
     */
    public static String downloadUrlFile(String urlString) throws IOException {
        BufferedInputStream in = null;
        String dataString = "";
        try {
            URL url = new URL(urlString);
            in = new BufferedInputStream(url.openStream());
            byte[] data = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(data)) != -1) {
                dataString += (new String(data, 0, bytesRead));
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    System.err.println("Error: " + e1.getMessage());
                }
            }
        }
        return dataString;
    }

    /**
     * Loads a file from the resources folder.
     * 
     * @param resourcePath
     *            name of file
     * @return File
     * @throws IOException
     *             if error
     */
    public static File getResourceAsFile(String resourcePath) throws IOException {
        try {
            InputStream in = Utils.class.getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                // copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            throw e;
        }
    }
}