package com.example.todo.entity;

import java.awt.Image;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 
 * @author shunsukekuzawa
 *
 */
@Data
public class TaskInfo implements Serializable{
	/**
     * ID
     */
    private Long id;
    
    /**
     * ユーザーID
     */
    private String userId;

    /**
     * 名前
     */
    private String name;
    
    /**
     * タイトル
     */
    private String title;
    
    /**
     * コンテンツ
     */
    private String contents;
    
    /**
     * 画像
     */
    private Image img;
    
    /**
     * @author kk
     * Path of an image
     */
    private String imgPath;
    
    /**
     * 更新日時
     */
    private Date updateDate;

    /**
     * 登録日時
     */
    private Date createDate;

    /**
     * 削除日時
     */
    private Date deleteDate;
   
}
