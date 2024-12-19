package com.company.mapper;

import com.company.domain.ImageDTO;

public interface ImageMapper {
	int upload(ImageDTO imageDTO);
	int delete(ImageDTO imageDTO);
}
