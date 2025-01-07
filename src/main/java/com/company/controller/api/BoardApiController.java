package com.company.controller.api;

import static com.company.util.ImageUtil.checkImageType;
import static com.company.util.ImageUtil.decodeImagePath;
import static com.company.util.ImageUtil.decodeRealFileName;
import static com.company.util.ImageUtil.encodeImagePath;
import static com.company.util.ImageUtil.encodeRealFileName;
import static com.company.util.ImageUtil.getPathByDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.domain.BoardDTO;
import com.company.domain.ImageDTO;
import com.company.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("api")
@Log4j
public class BoardApiController {
	@Autowired
	private BoardService boardService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@PostMapping("")
	public ResponseEntity<String> write(
			BoardDTO boardDTO,
			@RequestParam(value = "files", required = false) List<MultipartFile> files
	) {
		List<ImageDTO> images = new ArrayList<>();
		if(files != null) {
			for(MultipartFile mFile : files) {
				if(mFile.getSize() == 0) continue;
				try {
					var image = saveFile(mFile);
					if(image != null) {
						images.add(image);
					}
				} catch (IOException e) {
					log.error("Error while saving file: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			}
		}
		boardDTO.setFiles(null);
		boardDTO.setImages(images);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boardDTO.setWriter(auth.getName());
		if(boardService.register(boardDTO)) {
			return ResponseEntity.ok(String.valueOf(boardDTO.getIdx())); 
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PreAuthorize("principal.username == #boardDTO.writer")
	@PutMapping("{boardIdx}")
	public ResponseEntity<Void> update(
		@PathVariable("boardIdx") long boardIdx,
		BoardDTO boardDTO,
		@RequestParam(value = "files", required = false) List<MultipartFile> files
	) {
		List<ImageDTO> images = new ArrayList<>();
		if(files != null) {
			for(MultipartFile mFile : files) {
				if(mFile.getSize() == 0) continue;
				try {
					var image = saveFile(mFile);
					if(image != null) {
						images.add(image);
					}
				} catch (IOException e) {
					log.error("Error while saving file: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			}
		}
		boardDTO.setFiles(null);
		boardDTO.setImages(images);
		boardDTO.setIdx(boardIdx);
		List<ImageDTO> toBeDeleted = boardService.modify(boardDTO); 
		if(toBeDeleted != null) {
			for(ImageDTO image : toBeDeleted) {
				try {
					deleteFile(image);
				} catch(IOException e) {
					log.error("Failed to delete image:" + image.getRealFileName());
				} catch(Exception e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			}
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	/* Image Util */
	
	private String basePath = "C:\\upload";
	private String thumbnailFolder = "thumbnail";
	
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
