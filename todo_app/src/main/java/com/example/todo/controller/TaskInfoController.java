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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.dto.TaskAddRequest;
import com.example.todo.dto.TaskUpdateRequest;
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
	 * @author kk
	 * 
	 * Display the edit view and prepare the data input by user.
	 * 
	 * @param model
	 * @return edit html where user submits the new contents
	 */
	@GetMapping(value="/task/{id}/edit")
	public String editTask(@PathVariable int id, Model model) {
		TaskInfo task = taskInfoService.getTaskById(id);
		TaskUpdateRequest newTask = new TaskUpdateRequest();
		newTask.setId(task.getId());
		newTask.setTitle(task.getTitle());
		newTask.setContents(task.getContents());
		newTask.setImgPath(task.getImgPath());
		model.addAttribute("taskUpdateRequest", newTask);
		return "/edit";
	}
	
	/**
	 * @author kk
	 * 
	 * Delete a task.
	 * 
	 * @param id
	 * @param model
	 * @return return to the task display html
	 */
	@GetMapping(value="/task/{id}/delete")
	public String delete(@PathVariable int id, Model model) {
		System.out.println("Delete me!");
		taskInfoService.delete(id);
		return "redirect:/index";
	}
	
	/**
	 * @author kk
	 * 
	 * Update a task and its database.
	 * 
	 * @param taskUpdateRequest
	 * @param result
	 * @param model
	 * @return if update is successful, then return to the list page
	 */
	@RequestMapping(value="/task/update", method=RequestMethod.POST)
	public String updateTask(@Validated @ModelAttribute TaskUpdateRequest taskUpdateRequest, BindingResult result, Model model) {
		taskInfoService.updateTask(taskUpdateRequest);
		System.out.println("Updated completed");
		return "redirect:/index"; // Mapping と同一の転送先（45行目参考）
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
