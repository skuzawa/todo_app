package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.dto.TaskAddRequest;
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
	
	
	 /**
     * タスク新規登録画面を表示
     * @param model Model
     * @return タスク情報一覧画面
     */
    @GetMapping(value = "/add")
    public String displayAdd(Model model) {
        model.addAttribute("taskAddRequest", new TaskAddRequest());
        return "/add";
    }
	
	/**
     * タスク新規登録
     * @param taskRequest リクエストデータ
     * @param model Model
     * @return タスク情報一覧画面
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute TaskAddRequest taskRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "index";
        }
        // ユーザー情報の登録
        taskInfoService.save(taskRequest);
        return "redirect:/index";
    }
}
