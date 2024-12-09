package com.company.domain;

import lombok.Data;

@Data
public class PageDTO {
	int page;
	int maxPage;
	int startPage;
	int endPage;
}
