package com.company.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.domain.ReplyDTO;
import com.company.mapper.BoardMapper;
import com.company.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@Log4j
public class ReplyServiceTest {
	@Mock
	private BoardMapper boardMapper;
	@Mock
	private ReplyMapper replyMapper;

	@InjectMocks
	private ReplyServiceImpl service;

	private ReplyDTO testReply;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testReply = new ReplyDTO();
		testReply.setBoardIdx(19900919);
		testReply.setWriter("TESTW");
		testReply.setContent("TESTC");
	}

	@Test
	public void testReply() {
		when(replyMapper.write(argThat(
				reply ->
					reply == null ||
					reply.getWriter() == null ||
					reply.getContent() == null || 
					reply.getBoardIdx() < 1 )))
			.thenReturn(0);
		when(replyMapper.write(argThat(
				reply ->
					reply != null &&
					reply.getWriter() != null &&
					reply.getContent() != null && 
					reply.getBoardIdx() > 0 )))
			.thenReturn(1);
		when(boardMapper.updateReply(anyLong())).thenReturn(1);

		
		assertFalse(service.write(null));
		assertFalse(service.write(new ReplyDTO()));
		log.info(testReply);
		log.info(boardMapper.updateReply(2));
		assertTrue(service.write(testReply));
	}
}
