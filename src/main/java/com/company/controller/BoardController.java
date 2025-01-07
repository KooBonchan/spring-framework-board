package com.company.controller;

import static com.company.util.ImageUtil.decodeImagePath;
import static com.company.util.ImageUtil.decodeRealFileName;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.domain.BoardDTO;
import com.company.domain.ImageDTO;
import com.company.domain.PageDTO;
import com.company.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class BoardController {
	private String basePath = "C:\\upload";
	private String thumbnailFolder = "thumbnail";
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping
	public String defaultHandler(HttpServletRequest request) {
		log.info("----Not existing uri: redirecting----");
		log.info("Request URI: " + request.getRequestURI());
        log.info("Request Method: " + request.getMethod());
		return "redirect:/";
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
		return "list";
	}
	
	@GetMapping(path = "", params = "idx")
	public String view(Model model, @RequestParam long idx, RedirectAttributes redirectAttributes) {
		BoardDTO boardDTO = boardService.get(idx);
		if(boardDTO != null) {
			model.addAttribute("board", boardDTO);
			return "view";
		}
		redirectAttributes.addFlashAttribute("message", "document not found");
		return "redirect:/";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@GetMapping("write")
	public void writeForm () {};
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("update")
	public String updateForm(
			Model model,
			@RequestParam("idx") long idx,
			RedirectAttributes redirectAttributes) {
		BoardDTO boardDTO = boardService.get(idx);
		if(boardDTO != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if( ! boardDTO.getWriter().equals(auth.getName())) {
				redirectAttributes.addFlashAttribute("message", "Invalid access");
				redirectAttributes.addAttribute("idx", boardDTO.getIdx());
				return "redirect:/";
			}
			model.addAttribute("board", boardDTO);
			return "update";
		}
		redirectAttributes.addFlashAttribute("message", "Cannot find the document");
		return "redirect:/";
	}
	
	@PreAuthorize("principal.username == #writer")
	@PostMapping("delete")
	public String delete(
		long idx,
		String writer,
		RedirectAttributes redirectAttributes
	) {
		List<ImageDTO> toBeDeleted = boardService.remove(idx);
		if(toBeDeleted != null) {
			for(ImageDTO image : toBeDeleted) {
				try {
					deleteFile(image);
				} catch(IOException e) {
					log.error("Failed to delete image:" + image.getRealFileName());
				} catch(Exception e) {
					redirectAttributes.addFlashAttribute("message","Error occurred while deleting your document");
					return "redirect:/";
				}
			}
		}
		redirectAttributes.addFlashAttribute("message","successfully deleted the document.");
		return "redirect:/";
	}
	
	private void deleteFile(ImageDTO imageDTO) throws IOException{
		String filePath = decodeImagePath(imageDTO.getFilePath());
		String realFileName = decodeRealFileName(imageDTO.getRealFileName());
		File path = new File(basePath, filePath);
		File file = new File(path, realFileName);
		File thumbnail = new File(path, thumbnailFolder + File.separator + realFileName);
		
		file.delete();
		thumbnail.delete();
	}
	
}
