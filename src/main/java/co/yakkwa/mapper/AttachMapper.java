package co.yakkwa.mapper;

import java.util.List;

import co.yakkwa.domain.AttachFileDTO;
import co.yakkwa.domain.AttachVO;

public interface AttachMapper {
	void insert(AttachVO vo);
	
	void delete(String uuid);
	
	List<AttachFileDTO> findBy(Long bno);
	
	void deleteAll(Long bno);
	
	List<AttachVO> getOldFiles();
}
