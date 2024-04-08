package com.sm.book.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sm.book.model.dto.Book;

@Mapper
public interface MainMapper {

	/** 전체 도서 조회
	 * @return bookList
	 */
	List<Book> selectBookList();

	/** 도서 등록
	 * @param inputBook
	 * @return result
	 */ 
	int insertBook(Book inputBook);

}
