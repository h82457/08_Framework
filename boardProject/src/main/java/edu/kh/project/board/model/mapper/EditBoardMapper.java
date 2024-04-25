package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

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
	 * @param map
	 * @return result
	 */
	int boardDelete(Map<String, Object> map);

	/** 게시글 수정 (글부분만)
	 * @param inputBoard
	 * @return result
	 */
	int boardUpdate(Board inputBoard);

	/** 게시글 이미지 삭제
	 * @param map
	 * @return result
	 */
	int deleteImage(Map<String, Object> map);

	/** 게시글 이미지 수정
	 * @param img
	 * @return result
	 */
	int updateImage(BoardImg img);

	/** 게시글 이미지 삽입 (1행)
	 * @param img
	 * @return result
	 */
	int insertImage(BoardImg img);

}
