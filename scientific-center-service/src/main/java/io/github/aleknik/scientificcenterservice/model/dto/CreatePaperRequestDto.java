package io.github.aleknik.scientificcenterservice.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class CreatePaperRequestDto {

    private String title;

    private MultipartFile file;

    public CreatePaperRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
