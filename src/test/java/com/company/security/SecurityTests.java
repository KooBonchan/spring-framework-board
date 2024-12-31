package com.company.security;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.MemberVO;
import com.company.mapper.MemberMapper;
import com.company.service.AuthServiceImpl;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\security-context.xml"
})
@Log4j
public class SecurityTests {
	@Mock
	private MemberMapper memberMapper;
	@Mock 
	private PasswordEncoder encoder;
	@InjectMocks
	private AuthServiceImpl authService;
	
	private MemberVO testMember;
	{
		testMember = new MemberVO();
		testMember.setUsername("TESTER1191");
		testMember.setPassword("TOTALLYunencodedPW1!");
	}
	
	@Before
	public void mockSetup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSignupService() {
		when(memberMapper.signup(testMember)).thenReturn(1);
		when(memberMapper.grantMember(testMember.getUsername())).thenReturn(1);
		when(encoder.encode(testMember.getPassword())).thenReturn("ENCODED");
		try{
			authService.signup(testMember);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
		
		
	}
	
	
}
