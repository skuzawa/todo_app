package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.entity.TaskInfo;
import com.example.todo.service.TaskInfoService;

/**
 * 
 * @author kk
 * 
 * User Controller
 *
 */
@Controller
public class TaskInfoController {
	
	/**
	 * Task information service
	 */
	/** To put "Autowired" or not it does not seem matter" **/
	@Autowired
	private TaskInfoService taskInfoService;
	
	/**
	 * 
	 * Show all the tasks.
	 * 
	 * @return display (html) which contains all the images
	 */
	@GetMapping(value="/index")
	public String displayList(Model model) {
		List<TaskInfo> taskList = taskInfoService.findAll();
		model.addAttribute("tasklist", taskList);
		return "/taskListDisplay";
	}
	
}
