package com.example.todo.entity;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author kk
 *
 */
@Data
public class UserInfo implements Serializable{
	
	/**
	 * User ID
	 */
	private String userId;
	
	/**
	 * User's password
	 */
	private String password;
}
