package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author kk
 * 
 * User Controller
 *
 */
@Controller
public class UserInfoController {
	
	/**
	 * 
	 * Show all the tasks.
	 * 
	 * @return display (html) which contains all the images
	 */
	@GetMapping
	public String displayList() {
		return "/index";
	}
	
}
