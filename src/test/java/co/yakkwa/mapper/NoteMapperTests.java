package co.yakkwa.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.yakkwa.domain.MemberVO;
import co.yakkwa.domain.NoteVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class NoteMapperTests {
	@Autowired
	private NoteMapper mapper;
	@Autowired
	private MemberMapper memberMapper;
	
//	@Test
//	public void testInsert2() {
//		List<MemberVO> members = memberMapper.getList();
//		int i = 1;
//		for(MemberVO vo : members) {
//			for(MemberVO vo2 : members) {
//				NoteVO noteVO = new NoteVO();
//				noteVO.setSender(vo.getId());
//				noteVO.setReceiver(vo2.getId());
//				noteVO.setMessage("mapper 테스트 발송 :: " + i++);
//				mapper.insert(noteVO);
//			}
//		}
//	}
	
	@Test
	public void testInsert() {
		NoteVO noteVO = new NoteVO();
		noteVO.setSender("id1");
		noteVO.setReceiver("id2");
		noteVO.setMessage("mapper 테스트 발송");
		
		mapper.insert(noteVO);
	}
	
	@Test
	public void testSelectOne() {
		log.info(mapper.selectOne(2L));
	}
	
	@Test
	public void testUpdate() { // rdate를 업데이트하므로 실행 후 testSelectOne 메서드를 실행하면 rdate가 들어간다
		mapper.update(166L);
	}
	
	@Test
	public void testDelete() {
		mapper.delete(2L);
	}
	
	@Test
	public void testReceiverList() {
		mapper.sendList("id1").forEach(log::info);
	}
	@Test
	public void testReceiveList() {
		mapper.receiveList("id1").forEach(log::info);
	}
	
	@Test
	public void testReceiveUncheckedList() {
		mapper.receiveUncheckedList("id1").forEach(log::info);
	}
	
	
}
