package co.yakkwa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.yakkwa.domain.MemberVO;
//import co.yakkwa.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("member")
@Log4j
@AllArgsConstructor
public class MemberController {
	//private MemberService memberService;
	
	@GetMapping("login")
	public void login() {}
//	
//	@GetMapping("chat")
//	public void chat() {}
//	
//	@GetMapping("result")
//	public void result() {}
//	@PostMapping("login")
//	public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
//		log.info(vo);
//		MemberVO memberVO = memberService.get(vo);
//		log.info(session);
//		if(memberVO == null) {
//			rttr.addFlashAttribute("msg", "로그인 실패");
//		}
//		else {
//			session.setAttribute("member", memberVO);
//			log.info("로그인 성공");
//		}
//		return "redirect:/member/login";
//	}
//	@RequestMapping("logout")
//	public String logout(HttpSession session) {
//		log.info("로그아웃 처리");
//		session.invalidate();
//		return "redirect:/member/login";
//	}
//	@GetMapping("getList")
//	@ResponseBody
//	public List<MemberVO> getList() {
//		return memberService.getList();
//	}
//	
}
