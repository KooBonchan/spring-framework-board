package com.company.mapper;

import com.company.domain.MemberVO;

public interface MemberMapper {
	MemberVO read(String username);
}
