package com.iyihua.tools.trending.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

	private String language = "NONE";
	private String title;
	private String link;
	private String description;
	private String module;
	private String repository;
	
}
