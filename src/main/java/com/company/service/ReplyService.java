package com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.domain.ReplyDTO;
import com.company.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyService {
	@Autowired
	private ReplyMapper replyMapper;
	
	public List<ReplyDTO> findAll(long boardIdx){
		return replyMapper.findAll(boardIdx);  
	}
	
	public boolean write(ReplyDTO replyDTO) {
		return replyMapper.write(replyDTO) > 0;
	}
	
	public boolean delete(long boardIdx, long idx) {
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setBoardIdx(boardIdx);
		replyDTO.setIdx(idx);
		return delete(replyDTO);
	}
	public boolean delete(ReplyDTO replyDTO) {
		return replyMapper.delete(replyDTO) > 0;
	}
}
