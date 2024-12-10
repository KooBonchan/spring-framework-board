package com.company.service;

import java.util.List;

import com.company.domain.BoardDTO;
import com.company.domain.PageDTO;

public interface BoardService {
	List<BoardDTO> getAll();
	List<BoardDTO> getPage();
	List<BoardDTO> getPage(int page);
	List<BoardDTO> getPage(int page, String category, String query);
	List<BoardDTO> getPage(int page, int pageSize, String category, String query);
	BoardDTO get(long idx);
	
	boolean register(BoardDTO boardDTO);
	boolean modify(BoardDTO boardDTO);
	boolean remove(long idx);
	boolean remove(BoardDTO boardDTO);
	
	PageDTO getPageInfo(int page, int pageSize);
	PageDTO getPageInfo(int page, int pageSize, String category, String query);
	void setPageSize(int pageSize);
	void setBlockSize(int blockSize);
}
