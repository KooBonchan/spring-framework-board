package com.company.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.domain.ReplyDTO;
import com.company.service.ReplyService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("api/board")
@Log4j
public class ReplyApiController {
	@Autowired
	private ReplyService replyService;
	
	@GetMapping(path = "{boardIdx}")
	public ResponseEntity<List<ReplyDTO>> findAll(
		@PathVariable long boardIdx){
		var replies = replyService.findAll(boardIdx);
		if(replies != null) {
			return ResponseEntity.ok(replies);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping(path = "{boardIdx}")
	public ResponseEntity<Void> write(@RequestBody ReplyDTO replyDTO, @PathVariable long boardIdx, HttpSession session) {
		Object writer = session.getAttribute("writer");
		if(writer == null || ((String)writer).length() == 0) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		replyDTO.setWriter((String)writer);
		replyDTO.setBoardIdx(boardIdx);
		if(replyService.write(replyDTO)) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	

	@DeleteMapping(path = "{boardIdx}/{idx}")
	public ResponseEntity<Void> delete(
			@PathVariable(name = "boardIdx") long boardIdx,
			@PathVariable(name = "idx") long idx) {
		if(replyService.delete(boardIdx, idx)) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
