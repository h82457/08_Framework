package com.project.pawlife.adoption.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.project.pawlife.adoption.model.dto.Adopt;

@Mapper
public interface AdoptionMapper {

	
	/** 게시글 작성
	 * @param inputAdopt
	 * @return
	 */
	int adoptionInsert(Adopt inputAdopt);

	/** 게시글 수 조회
	 * @return
	 */
	int getListCount();

	/** 게시글 리스트 조회
	 * @param rowBounds
	 * @return
	 */
	List<Adopt> selectAdoptList(RowBounds rowBounds);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Adopt selectOneAdopt(Map<String, Integer> map);


	/** 북마크 해제(DELETE)
	 * @param map
	 * @return
	 */
	int deleteBookCheck(Map<String, Integer> map);

	/** 북마크 체크(INSERT)
	 * @param map
	 * @return
	 */
	int insertBookCheck(Map<String, Integer> map);
	
}
