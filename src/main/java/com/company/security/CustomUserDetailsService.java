package com.company.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.company.domain.MemberVO;
import com.company.mapper.MemberMapper;
import com.company.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;


@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.warn("Try Login with: " + username);
		MemberVO vo = mapper.read(username);
		if(vo != null) return new CustomUser(vo);
		throw new UsernameNotFoundException(username);
	}

}
