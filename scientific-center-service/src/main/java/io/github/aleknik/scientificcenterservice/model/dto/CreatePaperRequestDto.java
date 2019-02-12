package io.github.aleknik.scientificcenterservice.model.dto;

import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import io.github.aleknik.scientificcenterservice.model.domain.ScienceField;

import java.util.List;

public class CreatePaperRequestDto {

    private String title;

    private List<String> keywords;

    private String paperAbstract;

    private ScienceField scienceField;

    private List<AuthorDto> coauthors;

    private List<Reviewer> reviewers;

    public CreatePaperRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public ScienceField getScienceField() {
        return scienceField;
    }

    public void setScienceField(ScienceField scienceField) {
        this.scienceField = scienceField;
    }

    public List<AuthorDto> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<AuthorDto> coauthors) {
        this.coauthors = coauthors;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }
}
