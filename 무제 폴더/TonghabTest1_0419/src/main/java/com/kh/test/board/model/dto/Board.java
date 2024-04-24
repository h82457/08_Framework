package com.kh.test.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	private int boardNo;
	private String boardTitle;
	private String userId;
	private String boardContent;
	private int boardReadcount;
	private String boardDate;
}
