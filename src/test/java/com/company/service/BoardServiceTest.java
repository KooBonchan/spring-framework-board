package com.company.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.BoardDTO;
import com.company.domain.ReplyDTO;
import com.company.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class BoardServiceTest {
	@Mock
	private BoardMapper boardMapper;
	
	@InjectMocks
	private BoardServiceImpl boardService;
	
	private BoardDTO testBoard;
	private ReplyDTO testReply;
	private BoardDTO wrongBoard;
	
	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		testBoard = new BoardDTO();
		testBoard.setTitle("TESTT");
		testBoard.setContent("TESTC");
		testBoard.setWriter("TESTW");
		wrongBoard = new BoardDTO();
		
		testReply = new ReplyDTO();
		testReply.setWriter("TESTW");
		testReply.setContent("TESTC");
	}
	
	@Test
	public void testRegister() {
		when(boardMapper.write(any(BoardDTO.class))).thenReturn(1);
		
		assertTrue(boardService.register(testBoard));
	}
	
	@Test
	public void testRead() {
		when(boardMapper.write(any(BoardDTO.class))).thenReturn(1);
		when(boardMapper.write(argThat(
				board -> board.getTitle() == null ||
				board.getWriter().isEmpty())))
        .thenReturn(0);
		
		assertTrue(boardService.register(testBoard));
		assertFalse(boardService.register(wrongBoard));
	}
	
	
	
	
	@Test
	public void testCRUD() {
		assertNotNull(boardService);
		assertTrue("create test", boardService.register(testBoard));
		
		var boards = boardService.getPage(1, 3, null, null);
		assertNotNull(boards);
		assertTrue("read test", boards.size() > 0);
		log.info(testBoard);
		log.info(boards);
		testBoard.setTitle("UPDTT");
		testBoard.setContent("UPDTC");
		assertTrue("update test", boardService.modify(testBoard));
		
		assertTrue("delete test", boardService.remove(testBoard));
	}
	
}
