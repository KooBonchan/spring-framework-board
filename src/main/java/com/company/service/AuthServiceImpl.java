package com.company.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.domain.MemberVO;
import com.company.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AuthServiceImpl implements AuthService{

	@Autowired
	private MemberMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public long signup(MemberVO memberVO) throws RuntimeException{
		if(memberVO.getUsername().length() < 3 || !verify(memberVO.getPassword())) {
			throw new IllegalArgumentException("Invalid username or password");
		}
		MemberVO encodedMember = new MemberVO(memberVO);
		encodedMember.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		
		if( mapper.signup(encodedMember) > 0) {
			if (mapper.grantMember(encodedMember.getUsername()) > 0){
				return encodedMember.getIdx();
			}
		}
		throw new RuntimeException("Failed to signup");
	}
	
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
