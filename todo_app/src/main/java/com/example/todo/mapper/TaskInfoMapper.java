package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
	
	
}
