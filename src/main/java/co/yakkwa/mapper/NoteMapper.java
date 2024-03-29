package co.yakkwa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import co.yakkwa.domain.NoteVO;

public interface NoteMapper {
	// CRUD
	// 노트 작성
	@Insert("INSERT INTO TBL_NOTE (NOTENO, SENDER, RECEIVER, MESSAGE) VALUES(SEQ_NOTE.NEXTVAL, #{sender}, #{receiver}, #{message})")
	int insert(NoteVO vo);
	
	// 단일 조회
	@Select("SELECT * FROM TBL_NOTE WHERE NOTENO = #{noteno}")
	NoteVO selectOne(Long noteno);
	
	// 수신 확인
	@Update("UPDATE TBL_NOTE SET RDATE = SYSDATE WHERE NOTENO = #{noteno}")
	int update(Long noteno);
	
	// 노트 삭제
	@Delete("DELETE FROM TBL_NOTE WHERE NOTENO = #{noteno}")
	int delete(Long noteno);
	
	// 보낸거
	@Select("SELECT * FROM TBL_NOTE WHERE NOTENO > 0 AND SENDER = #{sender} ORDER BY 1 DESC")
	List<NoteVO> sendList(String sender);
	// 받은거
	@Select("SELECT * FROM TBL_NOTE tn WHERE NOTENO > 0 AND RECEIVER = #{receiver} ORDER BY 1 DESC")
	List<NoteVO> receiveList(String receiver);
	// 받았는데 확인 안한거
	@Select("SELECT * FROM TBL_NOTE tn WHERE NOTENO > 0 AND RECEIVER = #{receiver} AND RDATE IS NULL ORDER BY 1 DESC")
	List<NoteVO> receiveUncheckedList(String receiver);
}
