package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.StopWatch.TaskInfo;

@Mapper
public interface TaskInfoMapper {

	List<TaskInfo> findAll();
	
	
}
