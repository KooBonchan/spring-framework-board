package com.company.mapper;

import com.company.domain.MemberDTO;

public interface MemberMapper {
	MemberDTO login(MemberDTO memberDTO);
	String checkUsername(String username);
	int signup(MemberDTO memberDTO);
}
