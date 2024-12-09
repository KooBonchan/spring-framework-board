package com.company.persistence;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.BoardDTO;
import com.company.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class MapperTest {
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testBoardMapper() {
		BoardDTO test = new BoardDTO();
		test.setTitle("TEST");
		test.setContent("TEST");
		test.setWriter("TEST");
		
		try {
//			boardMapper.write(test);
			boardMapper.findQueriedPage(1,5,"writer","ou");
//			boardMapper.delete(test.getIdx());
		} catch (Exception e) {
			fail(e.getMessage());
		}		 
	}
}
