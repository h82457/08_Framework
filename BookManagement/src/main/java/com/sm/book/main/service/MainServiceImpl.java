package com.sm.book.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.StreamReadConstraints.Builder;
import com.sm.book.main.model.mapper.MainMapper;
import com.sm.book.model.dto.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;

	// 전체 도서 조회
	@Override
	public List<Book> selectBookList() {
		
		return mapper.selectBookList();
	}

	// 도서 등록 
	@Override
	public int insertBook(Book inputBook) {	return mapper.insertBook(inputBook);	}
	
}
