package io.github.aleknik.scientificcenterservice.model.dto;

import io.github.aleknik.scientificcenterservice.model.domain.PaperReview;

public class ReviewDto {

    private String comment;

    private String suggestion;

    private String privateComment;

    public ReviewDto() {
    }

    public ReviewDto(PaperReview paperReview, boolean includePrivate) {
        this.comment = paperReview.getComment();
        this.suggestion = paperReview.getSuggestion();
        this.privateComment = includePrivate ? paperReview.getPrivateComment() : "";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getPrivateComment() {
        return privateComment;
    }

    public void setPrivateComment(String privateComment) {
        this.privateComment = privateComment;
    }
}
