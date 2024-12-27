package com.company.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Deprecated
@RestController
@RequestMapping("api/auth")
@Log4j
public class AuthApiController {
	/*
	 * @Autowired private AuthService authService;
	 * @PostMapping("login") public ResponseEntity<Void> login(
	 * 
	 * @RequestBody MemberVO memberDTO, HttpSession session){
	 * if(authService.login(memberDTO)) { session.setAttribute("writer",
	 * memberDTO.getUsername()); return ResponseEntity.ok().build(); } return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
	 * 
	 * @PostMapping({"", "signup"}) public ResponseEntity<Void> signup(@RequestBody
	 * MemberVO memberDTO, HttpSession session){ String originalPassword =
	 * memberDTO.getPassword(); if(authService.signup(memberDTO)) { // modifies and
	 * changes memberDTO: hashing. memberDTO.setPassword(originalPassword); return
	 * login(memberDTO, session); } return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); }
	 * 
	 * @GetMapping("username-check/{username}") public ResponseEntity<Void>
	 * checkUsername(@PathVariable String username){
	 * if(authService.checkUsername(username)) { return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).build(); } return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); }
	 * 
	 * @GetMapping("logout") public ResponseEntity<Void> logout(HttpSession
	 * session){ session.invalidate(); return ResponseEntity.ok().build(); }
	 */

}
