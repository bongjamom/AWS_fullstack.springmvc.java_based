package co.yakkwa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.yakkwa.domain.ReplyVO;
import co.yakkwa.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	private ReplyService replyService;
	
	// replies/list/{bno}
	// replies/list/{bno}/{rno}
	
	@GetMapping({"list/{bno}", "list/{bno}/{rno}"})
	public List<ReplyVO> getList(@PathVariable Long bno, @PathVariable(required = false) Optional<Long> rno) { //Optional:null값이 올 수도 있을 때
		log.info(bno);
//		log.info(rno.isPresent() ? rno.get() : 0L); //boolean형태
		log.info(rno.orElse(0L));
//		if(rno == null) {
//			rno = 0L;
//		}
		return replyService.getList(bno, rno.orElse(0L));
//		return replyService.getList(bno, rno.orElseGet(() -> 0L));  위의 orElse와 같은 코드..
	}
	
	@PostMapping("new") //talend로 새 댓글 게시 테스트
	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 댓글 작성 가능
	public Long create(@RequestBody ReplyVO vo) {
		log.info(vo);
		replyService.register(vo);
		return vo.getRno();
	}
	
	@GetMapping("{rno}")
	public ReplyVO get(@PathVariable Long rno) {
		log.info(rno);
		return replyService.get(rno);
	}
	
	@DeleteMapping("{rno}")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
	public int remove(@PathVariable Long rno, @RequestBody ReplyVO vo) {
		log.info(rno);
		return replyService.remove(rno);
	}
	
	@PutMapping("{rno}")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
	public int modify(@PathVariable Long rno, @RequestBody ReplyVO vo) { //rno:경로 vo:실제 수정해야할 대상
		log.info(rno);
		log.info(vo);
		return replyService.modify(vo);
	}
}
