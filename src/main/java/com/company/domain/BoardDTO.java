package com.company.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * Communication btw View and controller
 * Translated from/to BoardVO at service layer maybe? 
 */
@Data
public class BoardDTO {
	
	private long idx;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	private int replyCount;
	private int imageCount;
	
	private List<MultipartFile> files;
	private List<Long> deletedFiles;
	private List<ImageDTO> images;
}
