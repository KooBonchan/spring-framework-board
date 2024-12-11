package com.company.mapper;

import java.util.List;

import com.company.domain.ReplyDTO;

public interface ReplyMapper {
	List<ReplyDTO> findAll(long boardIdx);
	
	int write(ReplyDTO replyDTO);
	int delete(ReplyDTO replyDTO);
}
