package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.entity.TaskInfo;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface TaskInfoMapper {

	/**
	 * Find all the existing elements from database.
	 * @return TaskInfo
	 */
	List<TaskInfo> findAll();
	
	/**
	 * @author kk
	 * 
	 * Get task by its id.
	 * 
	 * @param id Primary Key
	 * @return the corresponding task
	 */
	TaskInfo getTaskById(int id);
	
	/**
	 * @author kk
	 * 
	 * Get task by its user id.
	 * 
	 * @param user_id user id
	 * @return the corresponding task
	 */
	TaskInfo getTaskByUserId(int user_id);
	
	/**
	 * @author kk
	 * 
	 * @param taskUpdateRequest
	 */
	void updateTask(TaskUpdateRequest taskUpdateRequest);

	
}
