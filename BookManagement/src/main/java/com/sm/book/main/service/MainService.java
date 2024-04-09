package com.sm.book.main.service;

import java.util.List;

import com.sm.book.model.dto.Book;

public interface MainService {

	/** 전체 도서 조회
	 * @return bookList
	 */
	List<Book> selectBookList();

	/** 도서 등록
	 * @param inputBook
	 * @return result
	 */
	int insertBook(Book inputBook);

	/** 도서 검색
	 * @param bookTitle
	 * @return list
	 */
	List<Book> searchBook(String keyword);

}
