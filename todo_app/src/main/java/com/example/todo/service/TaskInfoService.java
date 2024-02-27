package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TaskAddRequest;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.entity.TaskInfo;
import com.example.todo.mapper.TaskInfoMapper;

/**
 * 
 * @author kk
 * 
 * Task info service.
 */
@Service
public class TaskInfoService {
	
	/**
     * ユーザー情報 Mapper
     */
	@Autowired
    private TaskInfoMapper taskInfoMapper;

    /**
     * ユーザー情報全件検索
     * @return 検索結果
     */
    public List<TaskInfo> findAll() {
        return taskInfoMapper.findAll();
    }
    
    /**
     * @author kk
     * 
     * Get task by its id.
     * 
     * @param id
     * @return the TaskInfo of corresponding id
     */
    public TaskInfo getTaskById(int id) {
    	return taskInfoMapper.getTaskById(id);
    }
    
    /**
     * @author kk
     * 
     * Get task by its user id.
     * 
     * @param user_id
     * @return the TaskInfo of corresponding id
     */
    public TaskInfo getTaskByUserId(int user_id) {
    	return taskInfoMapper.getTaskByUserId(user_id);
    }
    
    /**
     * @author kk
     * 
     * Update task corresponding to the taskUpdateRequest
     * 
     * @param taskUpdateRequest
     */
    public void updateTask(TaskUpdateRequest taskUpdateRequest) {
    	taskInfoMapper.updateTask(taskUpdateRequest);
    }
      
    /**
     * タスク情報登録
     * @param taskAddRequest リクエストデータ
     */
    public void save(TaskAddRequest taskAddRequest) {
        taskInfoMapper.save(taskAddRequest);
    }
    
    /**
     * @author kk
     * 
     * Delete a task.
     */
    public void delete(int id) {
    	taskInfoMapper.delete(id);
    }
}
