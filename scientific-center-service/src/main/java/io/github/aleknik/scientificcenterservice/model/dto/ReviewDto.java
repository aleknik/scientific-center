package io.github.aleknik.scientificcenterservice.model.dto;

import io.github.aleknik.scientificcenterservice.model.domain.PaperReview;

public class ReviewDto {

    private long id;

    private String comment;

    private String suggestion;

    private String privateComment;

    private String authorMessage;

    public ReviewDto() {
    }

    public ReviewDto(PaperReview paperReview, boolean includePrivate) {
        this.id = paperReview.getId();
        this.comment = paperReview.getComment();
        this.suggestion = paperReview.getSuggestion();
        this.privateComment = includePrivate ? paperReview.getPrivateComment() : "";
        this.authorMessage = paperReview.getAuthorMessage();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorMessage() {
        return authorMessage;
    }

    public void setAuthorMessage(String authorMessage) {
        this.authorMessage = authorMessage;
    }
}
