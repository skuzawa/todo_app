package com.example.todo.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadForm {
    // ===== 変更2：フォームクラスのフィールド名の型をリスト型に変更 ===== //
    private List<MultipartFile> multipartFile;
}