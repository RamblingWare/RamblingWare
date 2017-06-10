package org.rw.config;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * A simple utility class that contains common methods used across classes.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Utils {

    private final static DecimalFormat BYTEFORM = new DecimalFormat("0.00");
    private final static DateFormat READABLEDATEFORM = new SimpleDateFormat("MMM dd, yyyy");
    private final static DateFormat READABLEDATETIMEFORM = new SimpleDateFormat(
            "MMM dd, yyyy (hh:mm:ss a)");
    private final static DateFormat MYSQLDATEFORM = new SimpleDateFormat("yyyy-MM-dd");
    private final static DateFormat SQLSERVERDATEFORM = new SimpleDateFormat("yyyyMMdd hh:mm:ss a");

    /**
     * Return a cookie's value by its given name.
     * 
     * @param cookieName
     * @return Cookie
     */
    public static Cookie getCookie(HttpServletRequest servletRequest, String cookieName) {
        Cookie cookies[] = servletRequest.getCookies();
        Cookie myCookie = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    myCookie = cookies[i];
                    break;
                }
            }
        }
        return myCookie;
    }

    /**
     * Sets a cookie's value for the given name.
     * 
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletResponse servletResponse, String cookieName,
            String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);

        // cookie will last 1 year
        cookie.setMaxAge(60 * 60 * 24 * 365);
        servletResponse.addCookie(cookie);
    }

    /**
     * Converts the ResultSet to an ArrayList of HashMap records.
     * 
     * @param ResultSet
     * @return ArrayList of HashMaps
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> resultSetToHashMapArrayList(ResultSet rs)
            throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                if (md.getColumnName(i).equals("DocumentScanDate")) {
                    row.put(md.getColumnName(i), formatReadableDate(rs.getDate(i)));
                } else {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
            }
            list.add(row);
        }

        return list;
    }

    /**
     * Format bytes to a readable unit of measure. B, KB, MB, GB, etc...
     * 
     * @param bytes
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
     * @return
     */
    public static String formatReadableDate(Date date) {
        if (date == null) {
            return "Null";
        }
        return READABLEDATEFORM.format(date);
    }

    /**
     * Get a readable format of the given date. "MMM dd, yyyy (hh:mm:ss a)"
     * 
     * @param date
     * @return
     */
    public static String formatReadableDateTime(Date date) {
        if (date == null) {
            return "Null";
        }
        return READABLEDATETIMEFORM.format(date);
    }

    /**
     * Get a MySQL database format of the given date. "yyyy-MM-dd"
     * 
     * @param dateTime
     * @return
     */
    public static String formatMySQLDate(Date dateTime) {
        if (dateTime == null) {
            return "Null";
        }
        return MYSQLDATEFORM.format(dateTime);
    }

    /**
     * Get a SQLServer database format of the given datetime. "yyyyMMdd hh:mm:ss a"
     * 
     * @param dateTime
     * @return
     */
    public static String formatSQLServerDate(Date dateTime) {
        if (dateTime == null) {
            return "Null";
        }
        return SQLSERVERDATEFORM.format(dateTime);
    }

    /**
     * Tries to parse the String in any format, and convert it into a util.Date object. If it cannot
     * figure out what Date the String represents, then it will return null.
     * 
     * @param anyFormat
     * @return Date
     */
    public static Date convertStringToDate(String anyFormat) {
        String formats[] = {"MM/dd/yy", "MMMM d yy", "MMM dd yyyy", "MMM dd yy", "MMMM d, yy",
                "MM-dd-yy", "MM-dd-yyyy", "MM/dd/yyyy", "dd/MM/yy", "dd-MM-yyyy", "dd/MM/yyyy",
                "dd-MM-yy", "yyyy-MM-dd", "EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm:ss",
                "dd MMM yy HH:mm:ss", "ss:mm:HH dd MM:", "yyyyMMdd hh:mm:ss a", "yyyyMMdd hh:mm:ss",
                "yyyy-MM-dd hh:mm:ss a"};
        Date date = null;

        for (int i = 0; i < formats.length; i++) {
            try {
                date = new SimpleDateFormat(formats[i], Locale.ENGLISH).parse(anyFormat);
                // System.out.println("Format found: \""+formats[i]+"\" for date input
                // \""+anyFormat+"\"");
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
     * @return
     */
    public static String removeAllSpaces(String text) {
        return text.replaceAll("\\s", "");
    }

    /**
     * Strips out bad text characters from the given string.
     * 
     * @param text
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
     * @param u
     * @return
     */
    public static String formatURL(String u) {
        if (!u.startsWith("http://") && !u.startsWith("https://")) {
            return "http://" + u;
        } else {
            return u;
        }
    }

    /**
     * Downloads the urlString to the filename at the current directory.
     * 
     * @param urlString
     * @return String
     */
    public static String downloadUrlFile(String urlString) {
        BufferedInputStream in = null;
        // System.out.println("Downloading file from '"+urlString+"'");
        String dataString = "";
        try {
            URL url = new URL(urlString);
            in = new BufferedInputStream(url.openStream());
            byte data[] = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(data)) != -1) {
                dataString += (new String(data, 0, bytesRead));
            }

        } catch (Exception e) {
            System.err.println("ERROR when trying to download file. " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return dataString;
    }

    /**
     * Checks if the email address given is a valid form of an internet address.
     * <ul>
     * <li>Must have one @ symbol</li>
     * <li>Must have domain name like .com</li>
     * <li>Must not have illegal characters</li>
     * <ul>
     * See Javax.Mail.Internet package for more details.
     * 
     * @param email
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        boolean result = true;
        // just one email
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}