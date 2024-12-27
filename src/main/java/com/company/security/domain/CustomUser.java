package com.company.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.company.domain.MemberVO;

import lombok.Getter;

public class CustomUser extends User{
	private static final long serialVersionUID = 1L;
	@Getter
	private MemberVO member;

	public CustomUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	public CustomUser(MemberVO member) {
		super(member.getUsername(), member.getPassword(),
			member.isEnabled(), true, true, true,
			member.getAuths().stream()
			.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
			.collect(Collectors.toList()));
		this.member = member;
	}
	
}
