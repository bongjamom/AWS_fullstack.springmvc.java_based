package co.yakkwa.service;

import java.util.List;

import co.yakkwa.domain.AttachFileDTO;
import co.yakkwa.domain.BoardVO;
import co.yakkwa.domain.Criteria;

public interface BoardService {
	void register(BoardVO vo);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO vo);
	
	boolean remove(Long bno);
	
	List<BoardVO> getList(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	String deleteFile(AttachFileDTO dto);
}
