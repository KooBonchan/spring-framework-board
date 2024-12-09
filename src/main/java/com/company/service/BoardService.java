package com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.config.BoardConfig;
import com.company.domain.BoardDTO;
import com.company.domain.PageDTO;
import com.company.mapper.BoardMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private BoardConfig boardConfig;
	
	public List<BoardDTO> findAll() {
		return boardMapper.findAll();
	}
	public List<BoardDTO> findPage() { return findPage(1); }
	public List<BoardDTO> findPage(int page) { return findPage(page, null, null); }
	public List<BoardDTO> findPage(int page, String category, String query){
		int startIdx = getStartIdx(page);
		int endIdx = startIdx + boardConfig.getPageSize();
		
		return boardMapper.findQueriedPage(
				startIdx, endIdx, verifiedCategory(category), verifiedQuery(query));
	}
	
	public BoardDTO findByIdx(long idx) {
		return boardMapper.findByIdx(idx);
	}
	
	
	public boolean write(BoardDTO boardDTO) {
		return boardMapper.write(boardDTO) > 0;
	}
	public boolean update(BoardDTO boardDTO) {
		return boardMapper.update(boardDTO) > 0;
	}
	public boolean delete(long idx) {
		return boardMapper.delete(idx) > 0;
	}
	public boolean delete(BoardDTO boardDTO) {
		return delete(boardDTO.getIdx());
	}
	
	
	/* Paging */
	public PageDTO getPageInfo(int page) {
		return getPageInfo(page, null, null);
	}
	public PageDTO getPageInfo(int page, String category, String query) {
		PageDTO pageDTO = new PageDTO();
		if(page < 1) page = 1;
		int boardCount = boardMapper.countBoard(verifiedCategory(category), verifiedQuery(query));
		int pageSize = boardConfig.getPageSize();
		int blockSize = boardConfig.getBlockSize();
		
		int maxPage = (boardCount - 1) / pageSize + 1;
		if(page > maxPage) page = maxPage;
		pageDTO.setPage(page);
		pageDTO.setMaxPage(maxPage);
		
		int startPage = page - (page-1) % blockSize;
		int endPage = startPage + blockSize - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		pageDTO.setStartPage(startPage);
		pageDTO.setEndPage(endPage);
		
		return pageDTO;
	}
	
	/* Configuration */
	public void setPageSize(int pageSize) {
		if(pageSize < 1) return;
		boardConfig.setPageSize(pageSize);
	}
	public void setBlockSize(int blockSize) {
		if(blockSize < 2) return;
		boardConfig.setBlockSize(blockSize);
	}
	
	
	/* Utility */
	private int getStartIdx(int page) {
		if (page < 1) page = 1;
		return boardConfig.getPageSize() * (page - 1) + 1;
	}
	private String verifiedCategory(String category) {
		if( ! boardConfig.getCategorySet().contains(category)) category = null;
		return category;
	}
	private String verifiedQuery(String query) {
		if(query != null) {
			query = query.strip();
		}
		return query;
	}
}
