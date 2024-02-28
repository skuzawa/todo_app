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
	private String user_id;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * @author kk
	 * 
	 * Password for checking.
	 */
	private String passwordd;
}
