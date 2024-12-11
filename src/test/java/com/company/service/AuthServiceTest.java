package com.company.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class AuthServiceTest {
	@Autowired
	private AuthService authService;
	
	@Test
	public void testVerify() {
		String failPassword = "";
		String failPassword2 = "";
		String successPassword = "qweQWE123!@#";
		assertFalse(authService.verify(failPassword));
		assertFalse(authService.verify(failPassword2));
		assertTrue(authService.verify(successPassword));
	}
	@Test
	public void testSHA256() {
		String password = "VincenGarcia";
		String hashed = authService.SHA256(password);
		log.info(hashed);
		assertNotNull(hashed);
		assertTrue(hashed.length() == 64);
		String salted = authService.saltedSHA256(password);
		log.info(salted);
		assertNotNull(salted);
		assertTrue(salted.length() == 64);
	}
}
