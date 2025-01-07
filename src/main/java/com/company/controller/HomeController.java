package com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.domain.MemberVO;
import com.company.service.AuthService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {
	@Autowired
	private AuthService authService;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@GetMapping("")
	public String home() { return "home"; }
	
	@PostMapping("signup")
	public String signup(MemberVO memberVO, Model model, RedirectAttributes rttr) {
		try {
			if(authService.signup(memberVO) < 0) {
				rttr.addAttribute("error", "Cannot ");
				return "redirect:/login";
			}
		} catch(RuntimeException e){
			rttr.addAttribute("error", "Id already exists");
			return "redirect:/login";
		}
		
		try {
			Authentication auth = new UsernamePasswordAuthenticationToken(memberVO.getUsername(), memberVO.getPassword());
			Authentication authenticated = authenticationManager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(authenticated);
			return "redirect:/board";
		} catch (Exception e) {
			log.error("Error occurred while auto login");
			rttr.addAttribute("error", "");
			return "redirect:/login";
		}
		
	}
	@GetMapping("login")
	public void login () {}
	@GetMapping("signup")
	public String signup () {return "login";}
	
	@GetMapping("post-test")
	public String postTest() {return "postTest";}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@PostMapping("post-test")
	public String postTestSubmit() {log.warn("PostTest"); return "redirect:/post-test";}
}
