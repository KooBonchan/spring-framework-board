package com.company.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	long idx;
	String username;
	String password;
	Date regDate;
	boolean enabled;
	
	List<AuthVO> auths;
}
