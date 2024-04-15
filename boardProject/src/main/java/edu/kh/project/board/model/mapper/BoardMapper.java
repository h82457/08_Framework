package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);

	/** 특정 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);

	/** 좋아요 해제 (DELETE)
	 * @param map
	 * @return result
	 */
	int deleteBoardLike(Map<String, Integer> map);

	/** 좋아요 체크 (INSERT)
	 * @param map
	 * @return result
	 */
	int insertBoardLike(Map<String, Integer> map);

	/** 게시글 좋아요 개수 조회
	 * @param temp
	 * @return count
	 */
	int selectLikeCount(int temp); //< - 맵에서 묶여있지 않은 단일 데이터 1개, mapper.xml에서 사용시 여러개의 데이터가 묶여있지 않는 경우는 이름이 중요하지 X

	/** 조회수 1 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 조회수 조회
	 * @param boardNo
	 * @return
	 */
	int selectReadCount(int boardNo);

}
