package io.github.aleknik.scientificcenterservice.model.dto;

import java.util.List;

public class CreatePaperRequestDto {

    private String title;

    private List<String> keywords;

    private String paperAbstract;

    private String scienceField;

    private List<AuthorDto> coauthors;

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

    public String getScienceField() {
        return scienceField;
    }

    public void setScienceField(String scienceField) {
        this.scienceField = scienceField;
    }

    public List<AuthorDto> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<AuthorDto> coauthors) {
        this.coauthors = coauthors;
    }
}
