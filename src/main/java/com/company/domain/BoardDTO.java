package com.company.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

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
	
//	Files
//	TODO: distinguish DTO and VO
//	DTO - view ~ service
//	VO - service ~ persistence
	private List<ImageDTO> images;
	private MultipartFile[] files;
}
