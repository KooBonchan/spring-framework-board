package com.company.controller.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.util.ImageUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("api/image")
@Log4j
public class ImageApiController {
	private String basePath = "C:\\upload";
	private String thumbnailFolder = "thumbnail";
	
	@GetMapping("thumbnail/{path}/{filename}")
	public ResponseEntity<byte[]> getThumbnail(
		@PathVariable("path") String path,
		@PathVariable("filename") String filename
	){
		log.info(path + ": " +  filename);
		path = ImageUtil.decodeImagePath(path);
		filename = ImageUtil.decodeRealFileName(filename);
		File dir = new File(basePath, path + File.separator + thumbnailFolder);
		File file = new File(dir, filename);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			return ResponseEntity.ok()
					.headers(headers)
					.body(FileCopyUtils.copyToByteArray(file));
		} catch(IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("{path}/{filename}")
	public ResponseEntity<byte[]> getImage(
		@PathVariable("path") String path,
		@PathVariable("filename") String filename
	){
		path = path.replace("_SLASH_", "/");
		filename = filename.replace("_DOT_", ".");
		File dir = new File(basePath, path);
		File file = new File(dir, filename);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			return ResponseEntity.ok()
					.headers(headers)
					.body(FileCopyUtils.copyToByteArray(file));
		} catch(IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
}
