package com.company.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ImageDTO {
	long idx;
	long boardIdx;
	String originalFileName;
	String realFileName;
	String filePath;
	Date regDate;
}