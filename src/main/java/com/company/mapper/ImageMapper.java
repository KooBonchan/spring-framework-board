package com.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.company.domain.ImageDTO;

public interface ImageMapper {
	List<ImageDTO> findManyByIdx(
		@Param("boardIdx") long boardIdx,
		@Param("idxs") List<Long> idxs
	);
	
	int upload(ImageDTO imageDTO);
	int delete(ImageDTO imageDTO);
	int deleteAll(long boardIdx);
	int deleteManyByIdx(
		@Param("boardIdx") long boardIdx,
		@Param("idxs") List<Long> idxs
	);
	
}
