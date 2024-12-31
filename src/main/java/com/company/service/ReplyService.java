package com.company.service;

import java.util.List;

import com.company.domain.ReplyDTO;

public interface ReplyService {
	List<ReplyDTO> findAll(long boardIdx);
	
	boolean write(ReplyDTO replyDTO);
	boolean delete(ReplyDTO replyDTO);
}
