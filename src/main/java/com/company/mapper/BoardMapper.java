package com.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.company.domain.BoardDTO;

public interface BoardMapper {
	/* READ */
	List<BoardDTO> findAll();
	List<BoardDTO> findQueriedPage(
		@Param("startIdx") long startIdx,
		@Param("endIdx") long pageSize,
		@Param("category") String category,
		@Param("query") String query
	);
	int countBoard(
		@Param("category") String category,
		@Param("query") String query
	);
	
	BoardDTO findByIdx(long idx);
	
	/* CREATE, UPDATE, DELETE */
	int write(BoardDTO boardDTO);
	int update(BoardDTO boardDTO);
	int updateImageCount(BoardDTO boardDTO);
	int updateReply(
		@Param("idx") long idx,
		@Param("increment") boolean increment
	);
	int delete(long idx);
}
