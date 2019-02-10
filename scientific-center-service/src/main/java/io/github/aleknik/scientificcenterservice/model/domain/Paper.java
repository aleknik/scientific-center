package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paper")
public class Paper extends BaseModel {

    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Keyword> keywords;

    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<UnregisteredAuthor> coauthors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Reviewer> reviewers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Journal journal;

    @ManyToOne(fetch = FetchType.EAGER)
    private Issue issue;

    private LocalDateTime publishDate;

    private String paperAbstract;

    public Paper() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Set<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
