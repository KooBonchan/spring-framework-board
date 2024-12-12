package com.company.controller;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.company.domain.BoardDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
	"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml",
	})
@Log4j
public class BoardControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private BoardDTO testBoard;
	{
		testBoard = new BoardDTO();
		testBoard.setTitle("TESTT");
		testBoard.setContent("TESTC");
		testBoard.setWriter("TESTW");
	}
	
	@BeforeClass
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testGetList() {
		try {
			var objects = 
				mockMvc.perform(MockMvcRequestBuilders.get("/board"))
					.andReturn()
					.getModelAndView()
					.getModelMap();
			log.info(objects);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testGet() {
		try {
			var object = 
				mockMvc.perform(MockMvcRequestBuilders.get("/board?idx=1"))
					.andReturn()
					.getModelAndView()
					.getModel();
			log.info(object);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testWriteDelete() {
		try {
			String path = mockMvc.perform(MockMvcRequestBuilders.post("/board")
					.param("title", "TESTT")
					.param("content", "TESTC")
					.param("writer", "TESTW"))
				.andReturn()
				.getModelAndView()
				.getViewName();
			log.info(path);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		
	}
	
	
	
}
