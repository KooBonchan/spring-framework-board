package com.company.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.domain.MemberDTO;
import com.company.service.AuthService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("api/auth")
@Log4j
public class AuthApiController {
	@Autowired
	private AuthService authService;
	
	@PostMapping("login")
	public ResponseEntity<Void> login(MemberDTO memberDTO){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	
	@PostMapping({"", "signup"})
	public ResponseEntity<Void> signup(MemberDTO memberDTO){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("username-check/{username}")
	public ResponseEntity<Void> checkUsername(@PathVariable String username){
		if(authService.checkUsername(username)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
