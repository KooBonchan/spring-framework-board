package com.company.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Setter;

import lombok.Getter;

@Component
@Getter
@Setter
public class BoardConfig {
	private int pageSize = 10;
	private int blockSize = 5;
	private final Set<String> categorySet;
	private final Set<Integer> pageSizeSet;
	
	{
		categorySet = new HashSet<>();
		categorySet.add("title");
		categorySet.add("content");
		categorySet.add("writer");
		
		pageSizeSet = new HashSet<>();
		pageSizeSet.add(10);
		pageSizeSet.add(20);
		pageSizeSet.add(50);
		pageSizeSet.add(100);
	}
}
