package com.company.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.domain.BoardDTO;
import com.company.domain.PageDTO;
import com.company.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("board")
@Log4j
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping
	public String defaultHandler(HttpServletRequest request) {
		log.info("----Not existing uri: redirecting----");
		log.info("Request URI: " + request.getRequestURI());
        log.info("Request Method: " + request.getMethod());
		return "redirect:/board";
	}
	
	@GetMapping("")
	public String home(Model model,
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "category", required = false) String category,
	    @RequestParam(name = "query", required = false) String query
	) {
		PageDTO pageDTO = boardService.getPageInfo(page, category, query);
		var boards = boardService.findPage(pageDTO.getPage(), category, query);
		model.addAttribute("boards", boards);
		model.addAttribute("pageInfo", pageDTO);
		return "board/list";
	}
	
	@GetMapping(path = "", params = "idx")
	public String view(Model model, @RequestParam long idx) {
		BoardDTO boardDTO = boardService.findByIdx(idx);
		if(boardDTO != null) {
			model.addAttribute("board", boardDTO);
			return "board/view";
		}
		return toHomeWithMessage("Document not found");
	}
	
	@GetMapping("write")
	public void writeForm () {};
	@PostMapping({"", "write"})
	public String write(BoardDTO boardDTO) {
		String message = "failed to write new document";
		if(boardService.write(boardDTO)) {
			message= "Successfully uploaded your document"; 
		}
		return toHomeWithMessage(message);
	}
	
	@GetMapping("update")
	public String updateForm(Model model, @RequestParam("idx") long idx) {
		BoardDTO boardDTO = boardService.findByIdx(idx);
		if(boardDTO != null) {
			model.addAttribute("board", boardDTO);
			return "board/update";
		}
		model.addAttribute(boardDTO);
		return toHomeWithMessage("Document not found");
	}
	@PostMapping("update")
	public String update(BoardDTO boardDTO) {
		String message = "failed to update document";
		if(boardService.update(boardDTO)) {
			message= "successfully updated the document."; 
		}
		return toHomeWithMessage(message);
	}
	
	
	@GetMapping("delete")
	public String delete(@RequestParam("idx") long idx) {
		String message = "failed to delete document";
		if(boardService.delete(idx)) {
			message= "successfully deleted the document."; 
		}
		return toHomeWithMessage(message);
	}
	
	
	private String toHomeWithMessage(String message) {
		return "redirect:/board?message=" + URLEncoder.encode(message,StandardCharsets.UTF_8);
	}
}
