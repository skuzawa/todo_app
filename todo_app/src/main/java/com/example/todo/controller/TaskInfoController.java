package com.example.todo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.todo.dto.TaskAddRequest;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.dto.UploadForm;
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
		List<TaskInfo> taskList = taskInfoService.findAll((String) session.getAttribute("userId"));
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
		model.addAttribute("uploadForm", new UploadForm()); // Use to upload the picture
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
									BindingResult result, Model model, HttpSession session, UploadForm uploadForm) {
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
		
		// Update new picture (written by Kuzawa)
    	List<MultipartFile> multipartFile = uploadForm.getMultipartFile();
   	 	multipartFile.forEach(e -> {
            //アップロード実行処理メソッド呼び出し
   	 		taskUpdateRequest.setImgPath(uploadAction(e));
        });
   	 	
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
        model.addAttribute("uploadForm", new UploadForm());
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
    						BindingResult result, Model model, HttpSession session, UploadForm uploadForm) {
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
    	
    	List<MultipartFile> multipartFile = uploadForm.getMultipartFile();
    	 multipartFile.forEach(e -> {
             //アップロード実行処理メソッド呼び出し
             taskRequest.setImgPath(uploadAction(e));
         });
    	
        // ユーザー情報の登録
    	String userId = (String) session.getAttribute("userId");
    	taskRequest.setUser_id(userId);
    	
        taskInfoService.save(taskRequest);
        return "redirect:/index";
    }
    
    /**
     * アップロード実行処理
     * @param multipartFile
     */
    private String uploadAction(MultipartFile multipartFile) {
        //ファイル名取得
        String fileName = multipartFile.getOriginalFilename();

        //格納先のフルパス ※事前に格納先フォルダ「UploadTest」をCドライブ直下に作成しておく
        Path filePath = Paths.get("/Applications/Eclipse_2022-12.app/Contents/workspace/todo_app/todo_app/src/main/resources/static/upload_img/" + fileName);
        
        try {
            //アップロードファイルをバイト値に変換
            byte[] bytes  = multipartFile.getBytes();

            //バイト値を書き込む為のファイルを作成して指定したパスに格納
            OutputStream stream = Files.newOutputStream(filePath);
            //ファイルに書き込み
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = filePath.toString();
        return "/upload_img/"+fileName;
    }
    

	/**
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
        // Check if password is entered correctly (edited by kk)
        if (!userLoginRequest.getPassword().equals(userLoginRequest.getPasswordd())) {
        	model.addAttribute("passwordError", "確認パスワードが違います。");
        	return "/signUp";
        }
        if (userLoginRequest.getUser_id() == "" || userLoginRequest.getPassword() == "") {
        	model.addAttribute("passwordError", "空白は受け付けれません。");
        	return "/signUp";
        }
        
        // Check if ID is already used (edited by kk)
        try {
        	userInfoService.save(userLoginRequest);
        } catch (Exception e) {
        	model.addAttribute("passwordError", "このユーザーはすでに存在しています。");
        	return "/signUp";
        }
        
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
