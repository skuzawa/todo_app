package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.service.UserInfoService;

/**
 * 
 * @author kk
 * 
 * User Controller
 *
 */
@Controller
public class UserInfoController {
	
	/** UserInfoService instance to access more methods. */
	private UserInfoService userInfoService;
	
	/**
	 * 
	 * Show all the tasks.
	 * 
	 * @return display (html) which contains all the images
	 */
	@GetMapping
	public String displayList(Model model) {
		userInfoService = new UserInfoService();
		String to_return = userInfoService.helloWorld();
		model.addAttribute("output_label", to_return);
		return "/index";
	}
	
}
