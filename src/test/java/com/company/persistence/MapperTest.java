package com.company.persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.BoardDTO;
import com.company.domain.ReplyDTO;
import com.company.mapper.BoardMapper;
import com.company.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class MapperTest {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private ReplyMapper replyMapper;
	
	
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
	public void testBoardMapper() {
		try {
			boardMapper.write(testBoard);
			boardMapper.findQueriedPage(1,5,"writer","ou");
			boardMapper.delete(testBoard.getIdx());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testReplyMapper() {
		try {
			boardMapper.write(testBoard);
			assertTrue(testBoard.getIdx() > 0);
			testReply.setBoardIdx(testBoard.getIdx());
			replyMapper.write(testReply);
			assertTrue(testReply.getIdx() > 0);
			boardMapper.delete(testBoard.getIdx());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
