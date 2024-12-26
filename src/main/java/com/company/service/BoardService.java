package com.company.service;

import java.util.List;

import com.company.domain.BoardDTO;
import com.company.domain.ImageDTO;
import com.company.domain.PageDTO;

public interface BoardService {
	List<BoardDTO> getAll();
	List<BoardDTO> getPage();
	List<BoardDTO> getPage(int page);
	List<BoardDTO> getPage(int page, String category, String query);
	List<BoardDTO> getPage(int page, int pageSize, String category, String query);
	BoardDTO get(long idx);
	
	boolean register(BoardDTO boardDTO);
	
	/*
	 * Returns null if update failed
	 * Returns List of images to delete
	 */
	List<ImageDTO> modify(BoardDTO boardDTO);
	List<ImageDTO> remove(long idx);
	
	PageDTO getPageInfo(int page, int pageSize);
	PageDTO getPageInfo(int page, int pageSize, String category, String query);
	void setPageSize(int pageSize);
	void setBlockSize(int blockSize);
}
