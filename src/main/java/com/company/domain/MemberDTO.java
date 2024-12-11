package com.company.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDTO {
	long idx;
	String username;
	String password;
	Date regDate;
}
