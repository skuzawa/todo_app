package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.UserInfo;
import com.example.todo.mapper.UserInfoMapper;

/**
 * 
 * @author kk
 * 
 * User info service.
 */
@Service
public class UserInfoService {
	
	/**
     * ユーザー情報 Mapper
     */
	@Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * Get user's password
     * 
     * @param user's id
     * @return 検索結果
     */
    public UserInfo getPassword(String user_id) {
        return userInfoMapper.getPassword(user_id);
    }
  
}
