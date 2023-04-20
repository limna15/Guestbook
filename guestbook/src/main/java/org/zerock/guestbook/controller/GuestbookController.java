package org.zerock.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.repository.GuestBookRepository;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {
	
	@Autowired
	GuestBookRepository guestBookRepository;
	
	@GetMapping({"/","/list"})
	public String list() {
		log.info("list페이지 요청");
		return "guestbook/list";
	}
	
	@GetMapping("/quiz1")
	public String quiz1() {
		log.info("quiz1페이지 요청");
		return "guestbook/quiz1";
	}
	
	@PostMapping("/result")
	public String result(Model model, int page, int count) {
		
		Pageable pageable= PageRequest.of(page,count);
		model.addAttribute("list",guestBookRepository.findAll(pageable));
		return "guestbook/result";
	}
}
