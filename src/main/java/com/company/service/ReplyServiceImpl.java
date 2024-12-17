package com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.domain.ReplyDTO;
import com.company.mapper.BoardMapper;
import com.company.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private ReplyMapper replyMapper;
	
	public List<ReplyDTO> findAll(long boardIdx){
		return replyMapper.findAll(boardIdx);  
	}
	
	@Transactional
	public boolean write(ReplyDTO replyDTO) {
		if(replyDTO == null) return false;
		long boardIdx = replyDTO.getBoardIdx();
		if(replyMapper.write(replyDTO) > 0) {
			if(boardMapper.updateReply(boardIdx) > 0) {
				return true;
			}
		}
		return false;
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
