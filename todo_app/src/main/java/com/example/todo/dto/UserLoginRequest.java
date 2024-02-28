package com.example.todo.dto;

import lombok.Data;

/**
 * @author kk
 * 
 * User login information.
 *
 */
@Data
public class UserLoginRequest {

	/**
	 * User's id
	 */
	private String userId;
	
	/**
	 * User's password
	 */
	private String password;
}
