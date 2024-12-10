package com.company.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private long idx;
	private String writer;
	private String content;
	private Date date;
	private long boardIdx;
}
