package com.company.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
		@RequestParam(name = "category", required = false) String category,
	    @RequestParam(name = "query", required = false) String query
	) {
		PageDTO pageDTO = boardService.getPageInfo(page, pageSize, category, query);
		var boards = boardService.getPage(pageDTO.getPage(), pageSize, category, query);
		model.addAttribute("boards", boards);
		model.addAttribute("pageInfo", pageDTO);
		return "board/list";
	}
	
	@GetMapping(path = "", params = "idx")
	public String view(Model model, @RequestParam long idx, RedirectAttributes redirectAttributes) {
		BoardDTO boardDTO = boardService.get(idx);
		if(boardDTO != null) {
			model.addAttribute("board", boardDTO);
			return "board/view";
		}
		redirectAttributes.addFlashAttribute("message", "document not found");
		return "redirect:/board";
	}
	
	@GetMapping("write")
	public void writeForm () {};
	@PostMapping({"", "write"})
	public String write(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		String message = "failed to write new document";		
		for(MultipartFile file : boardDTO.getFiles()) {
			log.warn(file.getOriginalFilename());
			log.warn(file.getSize());
		}
		if(boardService.register(boardDTO)) {
			message= "Successfully uploaded your document"; 
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/board";
	}
	
	@GetMapping("update")
	public String updateForm(Model model, @RequestParam("idx") long idx, RedirectAttributes redirectAttributes) {
		BoardDTO boardDTO = boardService.get(idx);
		if(boardDTO != null) {
			model.addAttribute("board", boardDTO);
			return "board/update";
		}
		redirectAttributes.addFlashAttribute("message", "document not found");
		return "redirect:/board";
	}
	@PostMapping("update")
	public String update(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		
		String message = "failed to update document";
		if(boardService.modify(boardDTO)) {
			redirectAttributes.addFlashAttribute("message",  "successfully updated the document."); 
		} else {
			redirectAttributes.addFlashAttribute("message",  "failed to update document");
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/board";
	}
	
	
	@GetMapping("delete")
	public String delete(@RequestParam("idx") long idx, RedirectAttributes redirectAttributes) {
		String message = "failed to delete document";
		if(boardService.remove(idx)) {
			message= "successfully deleted the document."; 
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/board";
	}
	
}
