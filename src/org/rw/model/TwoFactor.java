package org.rw.model;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.kamranzafar.otp.OTP;

public class TwoFactor {

	public final static int BYTES = 20; // 160 bit
	
	public static String randomString(String characters, int length)
	{
		java.security.SecureRandom random = new java.security.SecureRandom();
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(random.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static String randomBase32()
	{
		byte[] bytes = new byte[BYTES];
		java.security.SecureRandom random = new java.security.SecureRandom();
		random.nextBytes(bytes);
		
		return new Base32().encodeToString(bytes);
	}
	
	public static String randomHex()
	{
		byte[] bytes = new byte[BYTES];
		java.security.SecureRandom random = new java.security.SecureRandom();
		random.nextBytes(bytes);
		
		return Hex.encodeHexString(bytes);
	}
	
	/**
	 * 
	 * @param secret (Base32)
	 * @param code (6-digits)
	 * @return
	 */
	public static boolean validateTOTP(String secret, String code)
	{
		try{
			// get base time in Hex
			long t = (long) Math.floor(Math.round(((double) System.currentTimeMillis()) / 1000.0) / 30l);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeLong(t);
			dos.close();
			byte[] longBytes = baos.toByteArray();
			String base = Hex.encodeHexString(longBytes);
			
			// convert Base32 secret to Hex
			byte[] bytes = new Base32().decode(secret);
			String key = Hex.encodeHexString(bytes);
			
			String t0code = OTP.generate(key, base, 6, "totp");
			
			// compare OTP codes
			if(code.equals(t0code))
				return true;
			else
				return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
