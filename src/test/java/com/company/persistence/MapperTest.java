package com.company.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.domain.BoardDTO;
import com.company.domain.ImageDTO;
import com.company.domain.ReplyDTO;
import com.company.mapper.BoardMapper;
import com.company.mapper.ImageMapper;
import com.company.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\security-context.xml"
})
@Transactional
@Log4j
public class MapperTest {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private ImageMapper imageMapper;
	
	
	private BoardDTO testBoard;
	private ReplyDTO testReply;
	private ImageDTO testImage;
	{
		testBoard = new BoardDTO();
		testBoard.setTitle("TESTT");
		testBoard.setContent("TESTC");
		testBoard.setWriter("TESTW");
		
		testReply = new ReplyDTO();
		testReply.setWriter("TESTW");
		testReply.setContent("TESTC");
		
		testImage = new ImageDTO();
		testImage.setOriginalFileName("TESTimg.jpg");
		testImage.setRealFileName("testIMAG-EisI-Nfor-matO-Fuui-d.jpg");
		testImage.setFilePath("t/e/s/t");
	}
	
	@Test
	public void testBoardMapper() {
		try {
			boardMapper.write(testBoard);
			long boardIdx = testBoard.getIdx(); 
			assertTrue(boardIdx > 0);
			
			// view test
			assertTrue(boardMapper.findByIdx(boardIdx) != null);
			// paging test
			assertTrue(boardMapper.findQueriedPage(1,5,"writer","ou").size() > 0);
			
			boardMapper.delete(testBoard.getIdx());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testReplyMapper() {
		try {
			boardMapper.write(testBoard);
			long boardIdx = testBoard.getIdx(); 
			assertTrue(boardIdx > 0);
			
			testReply.setBoardIdx(boardIdx);
			replyMapper.write(testReply);
			assertTrue(testReply.getIdx() > 0);
			assertTrue(boardMapper.updateReply(boardIdx, true) > 0);
			
			boardMapper.delete(testBoard.getIdx());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUploadFile() {
		try {
			boardMapper.write(testBoard);
			long boardIdx = testBoard.getIdx(); 
			assertTrue(boardIdx > 0);
			
			testImage.setBoardIdx(boardIdx);
			imageMapper.upload(testImage);
			long imageIdx = testImage.getIdx();
			assertTrue(imageIdx > 0);
			
			assertTrue(imageMapper.delete(testImage) > 0);
			assertTrue(boardMapper.delete(boardIdx) > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBoardImage() {
		try {
			long[] idxs =  {27L}; //TODO: fix idxs after board reset
			List<ImageDTO> result = imageMapper.findManyByIdx(8L, idxs);
			assertNotNull(result);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
