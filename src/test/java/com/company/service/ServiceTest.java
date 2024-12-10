package com.company.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.BoardDTO;
import com.company.domain.ReplyDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class ServiceTest {
	@Autowired
	private BoardService boardService;
	
	private BoardDTO testBoard;
	private ReplyDTO testReply;
	{
		testBoard = new BoardDTO();
		testBoard.setTitle("TESTT");
		testBoard.setContent("TESTC");
		testBoard.setWriter("TESTW");
		
		testReply = new ReplyDTO();
		testReply.setWriter("TESTW");
		testReply.setContent("TESTC");
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
