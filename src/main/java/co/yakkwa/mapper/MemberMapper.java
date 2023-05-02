package co.yakkwa.mapper;


import co.yakkwa.domain.MemberVO;

public interface MemberMapper {
	MemberVO read(String userid);
	
	
}
