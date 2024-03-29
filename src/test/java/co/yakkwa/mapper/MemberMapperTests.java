package co.yakkwa.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.yakkwa.domain.MemberVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTests {
	@Autowired
	private MemberMapper mapper;
	
//	@Test
//	public void testGetList() {
//		mapper.getList().forEach(log::info);
//	}
//	
//	@Test
//	public void testLogin() {
//		MemberVO vo = new MemberVO();
//		vo.setId("id1");
//		vo.setPw("1234");
//		log.info(mapper.login(vo));
//	}
}
