package io.github.aleknik.scientificcenterservice.model.dto;

public class FormatPaperDto {

    private PaperDto paper;

    private String message;

    public FormatPaperDto() {
    }

    public FormatPaperDto(PaperDto paper, String message) {
        this.paper = paper;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaperDto getPaper() {
        return paper;
    }

    public void setPaper(PaperDto paper) {
        this.paper = paper;
    }
}
