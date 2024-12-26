package com.company.service;

import java.util.ArrayList;
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
		int imageResult = 1, boardResult = 0;
		//TODO
		boardDTO.setImageCount(boardDTO.getImages().size());
		boardResult = boardMapper.write(boardDTO);
		long boardIdx = boardDTO.getIdx();
		for(ImageDTO imageDTO : boardDTO.getImages()) {
			imageDTO.setBoardIdx(boardIdx);
			imageResult *= imageMapper.upload(imageDTO);
		}
		return imageResult > 0 && boardResult > 0;
	}
	
	@Transactional
	public List<ImageDTO> modify(BoardDTO boardDTO) {
		int imageResult = 1, boardResult = 0;
		int addedImages = boardDTO.getImages().size();
		int deletedImages = 0;
		if(boardDTO.getDeletedFiles() != null) {
			deletedImages = boardDTO.getDeletedFiles().length;
		}
		boardDTO.setImageCount(addedImages - deletedImages); // delta
		boardResult = boardMapper.update(boardDTO);
		long boardIdx = boardDTO.getIdx();
		for(ImageDTO imageDTO : boardDTO.getImages()) {
			imageDTO.setBoardIdx(boardIdx);
			imageResult *= imageMapper.upload(imageDTO);
		}
		
		if(boardDTO.getDeletedFiles() == null) {
			return new ArrayList<ImageDTO>();
		}
		List<ImageDTO> toBeDeleted = imageMapper.findManyByIdx(boardDTO.getIdx(), boardDTO.getDeletedFiles());
		imageMapper.deleteManyByIdx(boardIdx, boardDTO.getDeletedFiles());
		if(boardResult > 0 && imageResult > 0 ) {
			return toBeDeleted;
		}
		return null;
	}
	
	@Transactional
	public List<ImageDTO> remove(long idx) {
		BoardDTO boardDTO = boardMapper.findByIdx(idx);
		imageMapper.deleteAll(idx);
		if(	boardMapper.delete(idx) > 0 ) {
			List<ImageDTO> toBeDeleted = boardDTO.getImages();
			if(toBeDeleted == null) toBeDeleted = new ArrayList<ImageDTO>();
			return toBeDeleted;
		}
		return null;
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
