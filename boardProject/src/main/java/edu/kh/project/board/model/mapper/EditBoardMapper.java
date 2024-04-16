package edu.kh.project.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	/** 게시글 작성 (글부분만)
	 * @param inputBoard
	 * @return result
	 */
	int boardInsert(Board inputBoard);

	/** 게시글 작성 (이미지만)
	 * @param uploadList
	 * @return result
	 */
	int insertUploadList(List<BoardImg> uploadList);

	/** 게시글 삭제
	 * @param boardNo
	 * @return result
	 */
	int boardDelete(int boardNo);

}
