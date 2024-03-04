package com.example.todo.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author kk
 * 
 * Task Update Class.
 */
public class TaskUpdateRequest {

	/** title to be updated. */
	private String title;
	
	/** contents to be updated. */
	private String contents;
	
	/** image path to be updated. */
	private String imgPath;
	
	/** id to be updated. */
	private long id;
	
	/** Images */
    private MultipartFile multipartFile;
	
	public String getTitle() { return title; }
	public String getContents() { return contents; }
	public String getImgPath() { return imgPath; }
	public long getId() { return id; }
	public MultipartFile getMultipartFile() { return multipartFile; }
	
	public void setTitle(String newTitle) { title = newTitle; }
	public void setContents(String newContents) { contents = newContents; }
	public void setImgPath(String newImgPath) { imgPath = newImgPath; }
	public void setId(Long long1) { id = long1; }
	public void setMultipartFile(MultipartFile newMultipartFile) { multipartFile = newMultipartFile; }
	
}
