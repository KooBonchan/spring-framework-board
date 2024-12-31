package com.company.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberVO {
	long idx;
	String username;
	String password;
	Date regDate;
	boolean enabled;
	
	List<AuthVO> auths;
	
	/*
	 * Copy constuctor
	 * Does not copy auths at all. If those features are needed, modify this constructor.
	 */
	public MemberVO(MemberVO other) {
        this.idx = other.getIdx();
        this.username = other.getUsername();
        this.password = other.getPassword();
        this.regDate = other.getRegDate();
        this.enabled = other.isEnabled();
        //TODO: copy each AuthVO if needed
    }
}
