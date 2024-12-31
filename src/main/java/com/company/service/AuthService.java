package com.company.service;

import com.company.domain.MemberVO;

public interface AuthService {
	long signup(MemberVO memberVO);
	
	boolean verify(String password);
	
}
