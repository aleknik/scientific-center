package io.github.aleknik.scientificcenterservice.model.dto;

public class CreatePaperRequestDto {

    private String title;

    public CreatePaperRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
