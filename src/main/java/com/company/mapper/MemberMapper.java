package com.company.mapper;

import com.company.domain.MemberDTO;

public interface MemberMapper {
	public MemberDTO login(MemberDTO memberDTO);
	public int signup(MemberDTO memberDTO);
}
