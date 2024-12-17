package com.company.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.config.BoardConfig;
import com.company.domain.PageDTO;
import com.company.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class BoardServiceTest {
	@Mock
	private BoardMapper mapper;
	@Mock
	private BoardConfig config;
	
	@InjectMocks
	private BoardServiceImpl boardService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		
	}
	
	@Test
	public void testPageInfoWithoutQuery() {
		when(config.getBlockSize()).thenReturn(5);
		when(mapper.countBoard(null, null)).thenReturn(30);
		
		PageDTO pageDTO = boardService.getPageInfo(1,4);
		assertEquals("page", 1, pageDTO.getPage());
		assertEquals("startPage", 1, pageDTO.getStartPage());
		assertEquals("endPage", 5, pageDTO.getEndPage());
		assertEquals("maxPage", 8, pageDTO.getMaxPage());
	}
	
	@Test
	public void testPageInfoWithQuery() {
		String testCategory = "title";
		String testQuery = "TEST";
		when(config.getBlockSize()).thenReturn(5);
		when(config.getCategorySet()).thenReturn(Set.of("title"));
		when(mapper.countBoard(testCategory,testQuery)).thenReturn(5);
			
		PageDTO pageDTO = boardService.getPageInfo(1,4, testCategory, testQuery);
		assertEquals("page", 1, pageDTO.getPage());
		assertEquals("startPage", 1, pageDTO.getStartPage());
		assertEquals("endPage", 2, pageDTO.getEndPage());
		assertEquals("maxPage", 2, pageDTO.getMaxPage());
	}
	
}
