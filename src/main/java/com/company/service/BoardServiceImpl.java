package com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.config.BoardConfig;
import com.company.domain.BoardDTO;
import com.company.domain.ImageDTO;
import com.company.domain.PageDTO;
import com.company.mapper.BoardMapper;
import com.company.mapper.ImageMapper;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private ImageMapper imageMapper;
	@Autowired
	private BoardConfig boardConfig;
	
	public List<BoardDTO> getAll() {
		return boardMapper.findAll();
	}
	public List<BoardDTO> getPage() { return getPage(1); }
	public List<BoardDTO> getPage(int page) { return getPage(page, null, null); }
	public List<BoardDTO> getPage(int page, String category, String query){
		return getPage(page, boardConfig.getPageSize(), category, query);
	}
	public List<BoardDTO> getPage(int page, int pageSize, String category, String query){
		int startIdx = getStartIdx(page, pageSize);
		int endIdx = startIdx + pageSize;
		return boardMapper.findQueriedPage(
				startIdx, endIdx, verifiedCategory(category), verifiedQuery(query));
	}
	
	public BoardDTO get(long idx) {
		return boardMapper.findByIdx(idx);
	}
	
	@Transactional
	public boolean register(BoardDTO boardDTO) {
		int imageResult = 0, boardResult = 0;
		//TODO
		boardDTO.setImageCount(boardDTO.getImages().size());
		boardResult = boardMapper.write(boardDTO);
		long boardIdx = boardDTO.getIdx();
		for(ImageDTO imageDTO : boardDTO.getImages()) {
			imageDTO.setBoardIdx(boardIdx);
			imageResult = imageMapper.upload(imageDTO);
		}
		
		
		return imageResult > 0 && boardResult > 0;
	}
	public boolean modify(BoardDTO boardDTO) {
		return boardMapper.update(boardDTO) > 0;
	}
	public boolean remove(long idx) {
		return boardMapper.delete(idx) > 0;
	}
	public boolean remove(BoardDTO boardDTO) {
		return remove(boardDTO.getIdx());
	}
	
	
	/* Paging */
	public PageDTO getPageInfo(int page, int pageSize) {
		return getPageInfo(page, pageSize, null, null);
	}
	public PageDTO getPageInfo(int page, int pageSize, String category, String query) {
		PageDTO pageDTO = new PageDTO();
		if(page < 1) page = 1;
		int boardCount = boardMapper.countBoard(verifiedCategory(category), verifiedQuery(query));
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
	private int getStartIdx(int page, int pageSize) {
		if (page < 1) page = 1;
		return pageSize * (page - 1) + 1;
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
