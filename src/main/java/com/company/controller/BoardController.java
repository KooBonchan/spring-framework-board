package com.company.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.company.domain.ImageDTO;
import com.company.domain.PageDTO;
import com.company.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("board")
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
		File uploadPath = getPathByDate();
		List<ImageDTO> images = new ArrayList<>();
		for(MultipartFile mFile : boardDTO.getFiles()) {
			try {
				var image = saveFile(mFile, uploadPath);
				if(image != null) {
					images.add(image);
				}
			} catch (IOException e) {
				log.error("Error while saving file: " + e.getMessage());
				redirectAttributes.addFlashAttribute(message);
				return "redirect:/board/write";
			}
		}
		boardDTO.setFiles(null);
		boardDTO.setImages(images);
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
	
	
	
	/* FILE UTILS */
	private File getPathByDate() {
		String rawPath = new SimpleDateFormat("yy-MM-dd")
				.format(new Date())
				.replace("-", File.separator);
		File path = new File(basePath, rawPath);
		if( ! path.exists()) {
			path.mkdirs();
		}
		return path;
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch(Exception ignored) {
			log.error("non-image file is stored: " + file.toString());
		}
		return false;
	}
	
	private ImageDTO saveFile(MultipartFile mFile,
			File uploadPath) throws IOException {
		log.info("uploaded file: " + mFile.getOriginalFilename());
		log.info("uploaded filesize: "+ mFile.getSize());
		if( ! mFile.getContentType().startsWith("image")) {
			log.warn("NONE-IMAGE: " + mFile.getOriginalFilename());
			return null;
		};
		String originalFileName = mFile.getOriginalFilename();
		String ext = originalFileName.substring(originalFileName.lastIndexOf('.'));
		UUID uuid = UUID.randomUUID();
		String realFileName = uuid.toString() + ext;
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setFilePath(uploadPath.toString());
		imageDTO.setOriginalFileName(originalFileName);
		imageDTO.setRealFileName(realFileName);
		
		File realFile = new File(uploadPath, realFileName);
		mFile.transferTo(realFile);
		if( ! checkImageType(realFile)) {
			realFile.delete();
		}
		File thumbnailPath = new File(uploadPath, thumbnailFolder);
		if( ! thumbnailPath.exists()) thumbnailPath.mkdir();
		try(FileOutputStream thumbnailStream =
				new FileOutputStream(
					new File(thumbnailPath, realFileName))){
			Thumbnailator.createThumbnail(
				mFile.getInputStream(),
				thumbnailStream,
				200, 200);
		}
		return imageDTO;
	}
	
	private boolean deleteFile(ImageDTO imageDTO) {
		File path = new File(basePath, imageDTO.getFilePath());
		
		return false;
	}
	
}
