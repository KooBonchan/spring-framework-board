package com.company.domain;

import lombok.Data;

@Data
public class Criteria {
	int page;
	int pageSize;
	String category;
	String query;
	{
		page = 1;
		pageSize = 10;
	}
	
}
