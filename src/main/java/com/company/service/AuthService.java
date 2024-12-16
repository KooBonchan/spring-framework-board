package com.company.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.domain.MemberDTO;
import com.company.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AuthService {
	@Autowired
	private MemberMapper memberMapper;
	
	
	public boolean login(MemberDTO memberDTO) {
		String password = memberDTO.getPassword();
		if(password == null) return false;
		memberDTO.setPassword(saltedSHA256(password));
		return memberMapper.login(memberDTO) != null;
	}
	public boolean checkUsername(String username) {
		return memberMapper.checkUsername(username) == null;
	}
	public boolean signup(MemberDTO memberDTO) {
		String password = memberDTO.getPassword();
		if( ! verify(password)) return false;
		password = saltedSHA256(password);
		if(password == null) return false;
		
		memberDTO.setPassword(password);
		try {
			if(memberMapper.signup(memberDTO) > 0) {
				return memberDTO.getIdx() > 0;
			}
		} catch (Exception e) {
			log.warn("signup: " + e.getMessage());
		}
		return false;
	}
	
	public boolean verify(String password) {
//		Password Requirement
//		Length between 8 and 20 characters
//		At least 1 uppercase letter, lowercase letter, digit
//		At least 1 special character !@#$%^&*()_+{}|:<>?/~`-
		final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:<>?/~`-])[A-Za-z\\d!@#$%^&*()_+{}|:<>?/~`-]{8,20}$";
		return Pattern.compile(regex)
				.matcher(password)
				.matches();
	}
	public String saltedSHA256(String password) {
//		Business logic: use substring of hashed password as salt
		String salt = SHA256(password);
		return SHA256(password + salt.substring(30,42));
	}
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
