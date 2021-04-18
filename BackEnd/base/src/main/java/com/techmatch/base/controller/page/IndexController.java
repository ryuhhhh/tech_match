package com.techmatch.base.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/about")
	public String about() {
		return "index";
	}
	
	@RequestMapping("/inquiry")
	public String inquiry() {
		return "index";
	}
	@RequestMapping("/notices")
	public String notices() {
		return "index";
	}
	@RequestMapping("/privacy-policy")
	public String privacyPolicy() {
		return "index";
	}
	@RequestMapping("/delete-ur-account")
	public String deleteUrAccount() {
		return "index";
	}
	@RequestMapping("/home")
	public String home() {
		return "index";
	}
	@RequestMapping("/login")
	public String login() {
		return "index";
	}
	@RequestMapping("/registration")
	public String registration() {
		return "index";
	}
	@RequestMapping("/main-registration")
	public String mainRegistration() {
		return "index";
	}
	@RequestMapping("/mypage")
	public String mypage() {
		return "index";
	}
	@RequestMapping("/RequirementList")
	public String RequirementList() {
		return "index";
	}
	@RequestMapping("/requirement-parent")
	public String Requirement() {
		return "index";
	}

	@RequestMapping("/MyRequirementSpecification")
	public String MyRequirementSpecification() {
		return "index";
	}
	

	@RequestMapping("/userpage")
	public String userpage() {
		return "index";
	}
	
	@RequestMapping("/RequirementSpecification")
	public String RequirementSpecification() {
		return "index";
	}
}

