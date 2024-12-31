package com.company.controller;

import static com.company.util.ImageUtil.decodeImagePath;
import static com.company.util.ImageUtil.decodeRealFileName;
import static com.company.util.ImageUtil.encodeImagePath;
import static com.company.util.ImageUtil.encodeRealFileName;
import static com.company.util.ImageUtil.getPathByDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("write")
	public void writeForm () {};
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping({"", "write"})
	public String write(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		String message = "failed to write new document";
		List<ImageDTO> images = new ArrayList<>();
		for(MultipartFile mFile : boardDTO.getFiles()) {
			if(mFile.getSize() == 0) continue;
			try {
				var image = saveFile(mFile);
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
				return "redirect:/board";
			}
			model.addAttribute("board", boardDTO);
			return "board/update";
		}
		redirectAttributes.addFlashAttribute("message", "Cannot find the document");
		return "redirect:/board";
	}
	
	@PreAuthorize("principal.username == #boardDTO.writer")
	@PostMapping("update")
	public String update(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("idx", boardDTO.getIdx());
		
		List<ImageDTO> images = new ArrayList<>();
		for(MultipartFile mFile : boardDTO.getFiles()) {
			if(mFile.getSize() == 0) continue;
			try {
				var image = saveFile(mFile);
				if(image != null) {
					images.add(image);
				}
			} catch (IOException e) {
				log.error("Error while saving file: " + e.getMessage());
				redirectAttributes.addFlashAttribute("message", "Failed to upload your images. Try again later");
				redirectAttributes.addAttribute("id", boardDTO.getIdx());
				return "redirect:/board/update";
			}
		}
		boardDTO.setFiles(null);
		boardDTO.setImages(images);
		List<ImageDTO> toBeDeleted = boardService.modify(boardDTO); 
		if(toBeDeleted != null) {
			redirectAttributes.addFlashAttribute("message",  "successfully updated the document.");
			for(ImageDTO image : toBeDeleted) {
				try {
					deleteFile(image);
				} catch(IOException e) {
					log.error("Failed to delete image:" + image.getRealFileName());
				} catch(Exception e) {
					redirectAttributes.addFlashAttribute("message","failed to delete image");
				}
			}
			return "redirect:/board";
		} else {
			redirectAttributes.addFlashAttribute("message",  "failed to update document");
			return "redirect:/board/update";
		}
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
					return "redirect:/board";
				}
			}
		}
		redirectAttributes.addFlashAttribute("message","successfully deleted the document.");
		return "redirect:/board";
	}
	
	/* FILE UTILS */
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch(Exception ignored) {
			log.error("non-image file is stored: " + file.toString());
		}
		return false;
	}
	
	private ImageDTO saveFile(MultipartFile mFile) throws IOException {
		String rawPath = getPathByDate();
		File uploadPath = new File(basePath, rawPath);
		if( ! uploadPath.exists()) {
			uploadPath.mkdirs();
		}
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
		imageDTO.setFilePath(encodeImagePath(rawPath.toString()));
		imageDTO.setOriginalFileName(originalFileName);
		imageDTO.setRealFileName(encodeRealFileName(realFileName));
		
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
