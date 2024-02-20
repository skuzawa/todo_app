package com.example.todo.mapper;

import java.util.List;

@Mapper
public interface TaskInfoMapper {

	List<TaskInfo> findAll();
	
	
}
