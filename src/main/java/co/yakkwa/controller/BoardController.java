package co.yakkwa.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.yakkwa.domain.AttachVO;
import co.yakkwa.domain.BoardVO;
import co.yakkwa.domain.Criteria;
import co.yakkwa.domain.PageDto;
import co.yakkwa.domain.SampleVO;
import co.yakkwa.service.BoardService;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("board/*")
@Data
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("list")
	public void list(Model model, Criteria cri) {
		log.info("list()");
		log.info(cri);
		model.addAttribute("list", boardService.getList(cri));
		model.addAttribute("page", new PageDto(boardService.getTotalCount(cri), cri));
	}
	
	@GetMapping({"get", "modify"})
	public void get(Model model, Long bno, @ModelAttribute("cri")Criteria cri) {
		log.info("get() or modify()");
		log.info(bno);
		log.info(cri);
		model.addAttribute("cri", cri);
		model.addAttribute("board", boardService.get(bno));
	}
	
	@GetMapping("{bno}")
	public String getByPath(Model model, @PathVariable Long bno) {
		log.info("get() or modify()");
		log.info(bno);
		model.addAttribute("board", boardService.get(bno));
		return "board/get";
	}
	
	@GetMapping("register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("register");
		log.info(vo);
		boardService.register(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		rttr.addAttribute("pageNum", 1);
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.writer")
	@PostMapping("modify")
	public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("modify");
		log.info(vo);
		log.info(cri);
		if(boardService.modify(vo)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #writer")
	@PostMapping("remove")
	public String remove(String writer, Long bno, RedirectAttributes rttr, Criteria cri) {
		log.info("remove");
		log.info(bno);
		log.info(cri);
		List<AttachVO> list = boardService.get(bno).getAttachs();
		if(boardService.remove(bno)) {
			//list.forEach(boardService::deleteFile);
			for(AttachVO vo : list) {
				boardService.deleteFile(vo);
			}
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@GetMapping("getSample")
	@ResponseBody // 일반 Controller에서는 ResponseBody 어노테이션을 붙여야 RestController와 동일한 기능을 하게 된다.
	public SampleVO getsSampleVO() {
		return new SampleVO(112, "스타", "로드");
	}
}
