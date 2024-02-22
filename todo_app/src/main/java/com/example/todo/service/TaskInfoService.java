package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	/** ERROR HERE "@Autowired"*/
	@Autowired
    private TaskInfoMapper taskInfoMapper;

    /**
     * ユーザー情報全件検索
     * @return 検索結果
     */
    public List<TaskInfo> findAll() {
        return taskInfoMapper.findAll();
    }
}
