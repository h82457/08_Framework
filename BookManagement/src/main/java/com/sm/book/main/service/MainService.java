package com.sm.book.main.service;

import java.util.List;

import com.sm.book.model.dto.Book;

public interface MainService {

	/** 전체 도서 조회
	 * @return bookList
	 */
	List<Book> selectBookList();

	
	/** 도서 등록
	 * @param bookTitle
	 * @param bookWriter
	 * @param bookPrice
	 * @return result
	 */
	int insertBook(Book inputBook);

}
