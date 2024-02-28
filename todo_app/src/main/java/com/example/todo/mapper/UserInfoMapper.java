package com.example.todo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.entity.UserInfo;

/**
 * @author kk
 * 
 * ユーザー情報 Mapper
 */
@Mapper
public interface UserInfoMapper {

	/**
	 * Get user's password from database;
	 * 
	 * @param user's id
	 * @return password in string
	 */
	UserInfo getPassword(String user_id);
 
}
