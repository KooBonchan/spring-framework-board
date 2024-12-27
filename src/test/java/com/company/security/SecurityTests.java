package com.company.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.MemberVO;
import com.company.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\security-context.xml"
})
@Log4j
public class SecurityTests {
	@Autowired
	private MemberMapper mapper;
	
	private String testId;
	{
		testId = "DEVDEV";
	}
	@Test
	public void testAuth() {
		MemberVO member = mapper.read(testId);
		assertNotNull(member);
		assertTrue(member.getAuths().size() > 0);
	}
}
