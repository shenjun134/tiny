/**
 * SaltedMD5Utils.java
 *
 *
 */
package com.tiny.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SaltedMD5Utils {

	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(SaltedMD5Utils.class);

	/**
	 * @param passwordToHash
	 * @return
	 */
	public static String getSecurePassword(String passwordToHash) {
		String saltKey = SystemUtils.getSaltKey();
		AssertUtils.hasText(saltKey, "saltKey is empty");
		return getSecurePassword(passwordToHash, saltKey.getBytes());
	}

	/**
	 * @param passwordToHash
	 * @param saltKey
	 * @return
	 */
	public static String getSecurePassword(String passwordToHash, String saltKey) {
		AssertUtils.hasText(saltKey, "saltKey is empty");
		return getSecurePassword(passwordToHash, saltKey.getBytes());
	}

	/**
	 * @param passwordToHash
	 * @param salt
	 * @return
	 */
	public static String getSecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(salt);
			// Get the hash's bytes
			byte[] bytes = md.digest(passwordToHash.getBytes());
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			LogUtil.error(logger, e, "getSecurePassword fail, with passwordToHash:{0},salt:{1}", passwordToHash, salt);
		}
		return generatedPassword;
	}

	// Add salt
	public static byte[] getRandomSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		// Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);
		// return salt
		return salt;
	}

	/**
	 * @param uuid
	 * @return
	 */
	public static String getRandomSalt(String uuid) {
		try {
			return getSecurePassword(uuid, getRandomSalt());
		} catch (Exception e) {
			return getSecurePassword(uuid, StringUtils.reverse(uuid).getBytes());
		}
	}
}
