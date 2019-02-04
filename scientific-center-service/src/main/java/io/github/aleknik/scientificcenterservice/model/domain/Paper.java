package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Paper extends BaseModel {

    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Keyword> keywords;

    private boolean openAccess;

    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<UnregisteredAuthor> coauthors;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Editor> reviewers;

    public Paper() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<UnregisteredAuthor> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(Set<UnregisteredAuthor> coauthors) {
        this.coauthors = coauthors;
    }

    public Set<Editor> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<Editor> reviewers) {
        this.reviewers = reviewers;
    }
}
