package com.company.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j;

@Log4j
public class AuthService {
	
	@Deprecated
	public boolean verify(String password) {
		final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:<>?/~`-])[A-Za-z\\d!@#$%^&*()_+{}|:<>?/~`-]{8,20}$";
		return Pattern.compile(regex)
				.matcher(password)
				.matches();
	}
	@Deprecated
	public String saltedSHA256(String password) {
		String salt = SHA256(password);
		return SHA256(password + salt.substring(30,42));
			// should be random and saved to db
			// currently, it does not do its SALT purpose.
	}
	@Deprecated
	public String SHA256(String src) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(src.getBytes());
			byte[] raw = messageDigest.digest();
			
			StringBuffer buffer = new StringBuffer();
			for(byte b : raw) {
				buffer.append(String.format("%02x", b & 0xFF));
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("Algorithm typo: " + e.getMessage());
		}
		return null;
	}
}
