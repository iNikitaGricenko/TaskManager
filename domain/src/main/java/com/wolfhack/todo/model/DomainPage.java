package com.wolfhack.todo.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class DomainPage<T> {

	List<T> content;

	int currentPage;

	int totalPages;

	int numberOfElements;

	long totalElements;

	public DomainPage(Page<T> page) {
		this.content = page.getContent();
		this.currentPage = page.getNumber();
		this.totalPages = page.getTotalPages();
		this.numberOfElements = page.getSize();
		this.totalElements = page.getTotalElements();
	}

}
