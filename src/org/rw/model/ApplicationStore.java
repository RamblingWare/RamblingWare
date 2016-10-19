package org.rw.model;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.SwingWorker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rw.bean.Email;

/**
 * AppicationStore loads the appropriate settings from application.properties file
 * and contains basic common methods used across actions.
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class ApplicationStore implements ServletContextListener {

	private final static String PROP_FILE = "/application.properties";
	private final static DecimalFormat BYTEFORM = new DecimalFormat("0.00");
	private final static DateFormat READABLEDATEFORM = new SimpleDateFormat("MMM dd, yyyy");
	private final static DateFormat READABLEDATETIMEFORM = new SimpleDateFormat("MMM dd, yyyy (hh:mm:ss a)");
	private final static DateFormat MYSQLDATEFORM = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat SQLSERVERDATEFORM = new SimpleDateFormat("yyyyMMdd hh:mm:ss a");
	private static HashMap<String, String> settingsMap;
	private static ArrayList<Email> emailQueue;
	private static EmailDaemon emailService;
	private static String host;
	public static String database;
	private static String port;
	private static String dbuser;
	private static String dbpass;
	private static String startDateTime;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		// Server Initializing...
		System.out.println(this.getClass().getName()+" Initializing...");
		
		try{
			// load settings from properties file
			settingsMap = new HashMap<String, String>();
			Properties properties = new Properties();
			System.out.println("Reading "+PROP_FILE);
			properties.load(ApplicationStore.class.getResourceAsStream(PROP_FILE));

			// put into map
			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				settingsMap.put(key, value);
			}
			
		} catch (IOException e) {
			System.err.println(e);
		}
		
		
		
		// check env variable
		String vcap = System.getenv("VCAP_SERVICES");
		if(vcap == null) {
			// if vcap is not available, then
			// run on local MySQL Database
			System.err.println("Failed to locate VCAP Object. Using local Db...");
			
			database = getSetting("db-name");
			host = getSetting("db-host");
			port = getSetting("db-port");
			dbuser = getSetting("db-user");
			dbpass = getSetting("db-pwd");
		}
		else {
			// try to read env variables
			try {
				JSONObject obj = new JSONObject(vcap);
				JSONArray cleardb = obj.getJSONArray("cleardb");
				JSONObject mysql = cleardb.getJSONObject(0).getJSONObject("credentials");
				database = mysql.getString("name");
				host = mysql.getString("hostname");
				port = ""+mysql.getInt("port");
				dbuser = mysql.getString("username");
				dbpass = mysql.getString("password");    
				
				System.out.println("VCAP_SERVICES were read successfully.");
				
			} catch (JSONException e) {
				e.printStackTrace();
				System.err.println("Failed to parse JSON object. Using local Db...");
				
				database = getSetting("db-name");
				host = getSetting("db-host");
				port = getSetting("db-port");
				dbuser = getSetting("db-user");
				dbpass = getSetting("db-pwd");				
			}
		}
		System.out.println("Database: "+database);
		System.out.println("Host: "+host);
		System.out.println("Port: "+port);
		System.out.println("User: "+dbuser);
		System.out.println("Password: ******");
		
		
		// startup email service
		System.out.println("Starting EmailDaemon service.");
		emailQueue = new ArrayList<Email>();
		emailService = new EmailDaemon(Integer.parseInt(getSetting("emailServiceWaitInterval")));
		emailService.execute();
		
		// webapp ready
		startDateTime = (READABLEDATETIMEFORM.format(new Date(System.currentTimeMillis())));
		System.out.println(this.getClass().getName()+" Initialized.");
	}
	
	/**
	 * Obtains a connection to the DB if possible.
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(getSetting("driver"));
		return DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database,dbuser,dbpass);
	}
	
	/**
	 * Converts the ResultSet to an ArrayList of HashMap records.
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
				if(md.getColumnName(i).equals("DocumentScanDate"))
					row.put(md.getColumnName(i), formatReadableDate(rs.getDate(i)));
				else
					row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}
	
		return list;
	}

	public static String getSetting(String key) {
		return settingsMap.get(key);
	}

	public static void setSetting(String key, String value) {
		settingsMap.put(key, value);
	}

	public static String getStartDateTime() {
		return startDateTime;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
		// save the settings back to the application.properties file
		// before the server is shutdown properly
		try{
			// put settings into properties file
			Properties properties = new Properties();
			System.out.println("Saving "+PROP_FILE);
	
			// get from map
			for (String key : settingsMap.keySet()) {
				properties.put(key, settingsMap.get(key));
			}
			
			// save properties to project root folder
			properties.store(new FileOutputStream(PROP_FILE), null);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println(this.getClass().getName()+" Destroyed.");
	}

	/**
	 * Format bytes to a readable unit of measure. B, KB, MB, GB, etc...
	 * @param bytes
	 * @return String
	 */
	public static String formatBytes(double bytes) {
		if (bytes >= 1073741824.0)
			return BYTEFORM.format(bytes / 1073741824.0) + " GB"; // gigabytes
		else if (bytes >= 1048576)
			return BYTEFORM.format(bytes / 1048576.0) + " MB"; // megabytes
		else if (bytes >= 1024)
			return BYTEFORM.format(bytes / 1024.0) + " KB"; // kilobytes
		else
			return BYTEFORM.format(bytes) + " B"; // bytes
	}

	/**
	 * Formats the time in milliseconds given to a readable unite of measure. X hrs X mins X secs X ms
	 * @param timeInMilliseconds
	 * @return String
	 */
	public static String formatTime(long timeInMilliseconds)
	{
		if (timeInMilliseconds >= 3600000)
			return (timeInMilliseconds / 3600000) + " hrs " + (timeInMilliseconds / 60000 % 60) + " mins " + (timeInMilliseconds / 1000 % 60) + " secs";
		else if (timeInMilliseconds >= 60000)
			return (timeInMilliseconds / 60000 % 60) + " mins " + (timeInMilliseconds / 1000 % 60) + " secs";
		else if (timeInMilliseconds >= 1000)
			return (timeInMilliseconds / 1000 % 60) + " secs";
		else
			return timeInMilliseconds + " ms";
	}

	/**
	 * Get a readable format of the given date. "MMM dd, yyyy"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatReadableDate(Date date) {
		if(date == null)
			return "Null";
		return READABLEDATEFORM.format(date);
	}

	/**
	 * Get a MySQL database format of the given date. "yyyy-MM-dd"
	 * @param dateTime
	 * @return
	 */
	public static String formatMySQLDate(Date dateTime) {
		if(dateTime == null)
			return "Null";
		return MYSQLDATEFORM.format(dateTime);
	}

	/**
	 * Get a SQLServer database format of the given datetime. "yyyyMMdd hh:mm:ss a"
	 * @param dateTime
	 * @return
	 */
	public static String formatSQLServerDate(Date dateTime) {
		if(dateTime == null)
			return "Null";
		return SQLSERVERDATEFORM.format(dateTime);
	}

	/**
	 * Tries to parse the String in any format, and convert it into a util.Date
	 * object. If it cannot figure out what Date the String represents, then it
	 * will return null.
	 * 
	 * @param anyFormat
	 * @return Date
	 */
	public static Date convertStringToDate(String anyFormat) {
		String formats[] = { 
				"MM/dd/yy", "MMMM d yy","yyyy-MM-dd", "MMM dd yyyy", "MMM dd yy",
				"MMMM d, yy", "dd/MM/yy", 
				"MM-dd-yy", "dd-MM-yy",
				"EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm:ss",
				"dd MMM yy HH:mm:ss", "ss:mm:HH dd MM:",
				"yyyyMMdd hh:mm:ss a", "yyyyMMdd hh:mm:ss", "yyyy-MM-dd hh:mm:ss a"};
		Date date = null;
	
		for (int i = 0; i < formats.length; i++) {
			try {
				date = new SimpleDateFormat(formats[i], Locale.ENGLISH)
						.parse(anyFormat);
				break; // successful
			} catch (Exception e) {
				continue; // try another
			}
		}
	
		return date;
	}
	
	/**
	 * Strips out all spaces, newlines, returns, and tabs. 
	 * @param text
	 * @return
	 */
	public static String removeAllSpaces(String text) {
		text = text.replaceAll("\\s", "");
		return text;
	}

	/**
	 * Strips out bad text characters from the given string.
	 * @param text
	 * @return String
	 */
	public static String removeBadChars(String text) {
		text = text.replaceAll("[\\^/?<>\\:*`'~!\\\\.,;@#$%()\\[\\]+{}\"]","").trim();
		text = text.replaceAll("\\s+", " ");
		return text;
	}
	
	/**
	 * Strips out all Non-ASCII characters from the given string.
	 * @param text
	 * @return String
	 */
	public static String removeNonAsciiChars(String text) {
		// Remove all non-ASCII, non-printable characters
		//   "\p{Print}" represents a POSIX character
		//   "\P{Print}" represents the complement of "\p{Print}"
		text = text.replaceAll("\\P{Print}", "");
		return text;
	}
	
	/**
	 * Make sure the URL starts with 'http://' or 'https://'
	 * 
	 * @param u
	 * @return
	 */
	public static String formatURL(String u) {
		String url2 = u;
		if (!u.startsWith("http://") && !u.startsWith("https://"))
			url2 = "http://" + url2;
		return url2;
	}
	
	/**
	 * Downloads the urlString to the filename at the current directory.
	 * 
	 * @param urlString
	 * @return String
	 */
	public static String downloadUrlFile(String urlString) {
		BufferedInputStream in = null;
		//System.out.println("Downloading file from '"+urlString+"'");
		String dataString = "";
		try {
			URL url = new URL(urlString);
			in = new BufferedInputStream(url.openStream());
			byte data[] = new byte[1024];
			int bytesRead=0;
			while( (bytesRead = in.read(data)) != -1){ 
				dataString += (new String(data, 0, bytesRead));               
			}
			
		} catch(Exception e){
			System.err.println("ERROR when trying to download file. "+e.getMessage());
		}finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e1) {
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

	/**
	 * Adds the email message to the queue to be sent by the server.
	 * @param email
	 */
	public static void addToEmailQueue(String from, String to, String subject, String message) {
		Email email = new Email();
		email.setFrom(from);
		email.setTo(to);
		email.setSubject(subject);
		email.setMessage(message);
		emailQueue.add(email);
		System.out.println("A message was queued.");
	}

	/**
	 * A private Email Service that runs in the background of the J2EE web application.
	 * It scans for emails in the queue, and tries to send them to the support email address.
	 * If it fails to send an email, it will wait until time is up, to send again. (Usually 1 min)
	 * @author Austin Delamar
	 * @date 5/28/2014
	 */
	private class EmailDaemon extends SwingWorker<Void, Void>{
		private int waitInterval = 60000; // default 1 minute
		
		private EmailDaemon(int waitInterval) {
			this.waitInterval = waitInterval;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			
			while(!this.isCancelled()) {
				// wait for emails to be queued
				
				while(!emailQueue.isEmpty()) {
					// if there is one to send,
					// then try to send it.
					System.out.println("Sending "+emailQueue.size()+" message(s).");
					Email email = emailQueue.get(0);
					try {
						sendQuickEmail(email.getFrom(),email.getTo(),email.getSubject(),email.getMessage());
						System.out.println("Successfully sent a message.");
						// if successful, then remove it
						emailQueue.remove(0);
					} catch (Exception e) {
						// if failed, then wait to try again
						System.err.println("Failed to send message. "+e.getMessage());
						System.err.println("Waiting "+waitInterval+"ms to try again.");
						break;
						// break now, because even if 1 email failed
						// then the others may fail as well.
					}
				}
				
				// wait
				Thread.sleep(waitInterval);
			}
			return null;
		}
	
		/**
		 * Sends a quick email message with the given properties. Returns true or false, if it was sent successfully.
		 * @param from
		 * @param to
		 * @param subject
		 * @param message
		 * @return 
		 */
		private void sendQuickEmail(String from, String to, String subject, String message) throws AddressException, MessagingException {
			
			Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", true); // added this line
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.user", "austin.delamar@gmail.com");
		    props.put("mail.smtp.password", "???");
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", true);

		    Session session = Session.getInstance(props,null);
		    MimeMessage msg = new MimeMessage(session);

		    System.out.println("Port: "+session.getProperty("mail.smtp.port"));

		    // Create the email addresses involved
		    try {
		        InternetAddress fromIA = new InternetAddress(from);
		        msg.setSubject(subject);
		        msg.setFrom(fromIA);
		        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

		        // Create a multi-part to combine the parts
		        Multipart multipart = new MimeMultipart("alternative");

		        // Create your text message part
		        BodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart.setText(message);

		        // Add the text part to the multipart
		        multipart.addBodyPart(messageBodyPart);

		        // Create the html part
		        messageBodyPart = new MimeBodyPart();
		        String htmlMessage = message;
		        messageBodyPart.setContent(htmlMessage, "text/html");


		        // Add html part to multi part
		        multipart.addBodyPart(messageBodyPart);

		        // Associate multi-part with message
		        msg.setContent(multipart);

		        // Send message
		        Transport transport = session.getTransport("smtp");
		        transport.connect("smtp.gmail.com", "austin.delamar@gmail.com", "???");
		        System.out.println("Transport: "+transport.toString());
		        transport.sendMessage(msg, msg.getAllRecipients());

		    } catch (AddressException e) {
		        e.printStackTrace();
		    } catch (MessagingException e) {
		        e.printStackTrace();
		    }
		}
	}
}