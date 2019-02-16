package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class PaperReview extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    private User reviewer;

    private String comment;

    private String suggestion;

    private String privateComment;

    public PaperReview() {
    }

    public PaperReview(User reviewer, String comment, String suggestion, String privateComment) {
        this.reviewer = reviewer;
        this.comment = comment;
        this.suggestion = suggestion;
        this.privateComment = privateComment;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
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
