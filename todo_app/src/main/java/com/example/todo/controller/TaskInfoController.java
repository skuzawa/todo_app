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
import com.example.todo.dto.UserLoginRequest;
import com.example.todo.entity.TaskInfo;
import com.example.todo.entity.UserInfo;
import com.example.todo.service.TaskInfoService;
import com.example.todo.service.UserInfoService;

import jakarta.servlet.http.HttpSession;

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
	 * User information service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 
	 * Show all the tasks.
	 * 
	 * @return display (html) which contains all the images
	 */
	@GetMapping(value="/index")
	public String displayList(Model model, HttpSession session) {
		
		if (session.getAttribute("userId") == null) {
			return "redirect:/top";
		}
		System.out.println(session.getAttribute("userId"));
		List<TaskInfo> taskList = taskInfoService.findAll();
		model.addAttribute("tasklist", taskList);
		return "/taskListDisplay";
	}
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Welcome to todo_app.
	 * 
	 * @return display (html)top-page.
	 * 
	 */
	@GetMapping(value="/top")
	public String displayTop() {
		return "/top";
	}
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * input user-id and password.
	 * 
	 * @return display (html)signUp-page.
	 * 
	 */
	@GetMapping(value="/signUp")
	public String displaySignUp() {
		return "/signUp";
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
	public String editTask(@PathVariable int id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
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
	public String delete(@PathVariable int id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
		System.out.println("Delete me!");
		taskInfoService.delete(id);
		return "redirect:/index";
	}
	
	/**
	 * @author kk
	 * 
	 * Delete all tasks.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value="/delete")
	public String deleteAll(Model model, HttpSession session) {
		if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
		taskInfoService.deleteAll();
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
	public String updateTask(@Validated @ModelAttribute TaskUpdateRequest taskUpdateRequest, 
									BindingResult result, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
		// コピペいたしました
		if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "index";
        }
		taskInfoService.updateTask(taskUpdateRequest);
		return "redirect:/index"; // Mapping と同一の転送先（45行目参考）
	}
	
	/**
	 * @author kk
	 * 
	 * User login
	 * 
	 * @return go to index if login is successful, else go back to login himl
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)

	public String login(@Validated @ModelAttribute UserLoginRequest loginRequest, 
			BindingResult result, Model model, HttpSession session) {
		String userId = loginRequest.getUser_id();

		String userInputPassword = loginRequest.getPassword();
		UserInfo userInfo = userInfoService.getPassword(userId);
		if (userInfo == null) {
			model.addAttribute("label", "User does not exist.");
			return "/login";  // Unknown redirect.
		}
		String expectedPassword  = userInfo.getPassword();
		if (expectedPassword == null) {
			model.addAttribute("label", "Something went wrong.");
			return "/login";  // Unknown redirect.
		}
		if (expectedPassword.equals(userInputPassword)) {
			session.setAttribute("userId", userId);
			return "redirect:/index"; // Password is correct. 
		}
		model.addAttribute("label", "Wrong password!"); // Add label to html
		return "/login";
	}
	
	/**
	 * Tmp function to direct to login menu
	 * @param model
	 * @return login html
	 */
	@GetMapping(value="/signIn")
	public String goToLogin(Model model) {
		return "/login";
	}
  
  
	/**
     * タスク新規登録画面を表示
     * @param model Model
     * @return タスク情報一覧画面
     */
    @GetMapping(value = "/add")
    public String displayAdd(Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
        model.addAttribute("taskAddRequest", new TaskAddRequest());
        return "/add";
    }
	
	/**
     * タスク新規登録
     * @author shunsukekuzawa
     * @param taskRequest リクエストデータ
     * @param model Model
     * @return タスク情報一覧画面
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute TaskAddRequest taskRequest, 
    						BindingResult result, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null ) {
			return "redirect:/top";
		}
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
    
    /**
<<<<<<< HEAD
     * ユーザーログイン情報新規登録
     * @author shunsukekuzawa
     * @param UserLoginRequest リクエストデータ
     * @param model Model
     * @return トップ画面
     */
    @RequestMapping(value = "/top", method = RequestMethod.POST)
    public String saveUserLogin(@Validated @ModelAttribute UserLoginRequest userLoginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "top";
        }
        // ユーザー情報の登録
        userInfoService.save(userLoginRequest);
        return "redirect:/top";
    }
    /*
     * @author kk
     * 
     * Logout, invalidate session
     * 
     * @return
     */
    @GetMapping(value="/logout")
    public String logout(Model model, HttpSession session) {
    	session.invalidate();
    	return "/top";
    }
}
