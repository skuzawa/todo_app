package com.example.todo.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報登録 リクエストデータ
 */
@Data
public class TaskAddRequest implements Serializable {
    /**
     * タスク名
     */
    @NotEmpty(message = "タスク名を入力してください")
    @Size(max = 100, message = "タスク名は100桁以内で入力してください")
    private String title;
    /**
     * 内容
     */
    @Size(max = 255, message = "内容は255桁以内で入力してください")
    private String contents;
    
    /**
     * @author kk
     */
    private String user_id;
    
    
    //画像
    private MultipartFile multipartFile;
    
    private String imgpath;
    
    public String getImgPath() { return imgpath; }
    public void setImgPath(String newImgPath) { imgpath = newImgPath; }
    
}