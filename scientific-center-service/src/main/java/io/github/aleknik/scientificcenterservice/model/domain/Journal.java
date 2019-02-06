package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "journal")
public class Journal extends BaseModel {

    private String name;

    private boolean openAccess;

    private BigDecimal paperPrice;

    private BigDecimal subscriptionPrice;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "journal")
    private Editor editor;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "journal")
    private Set<Issue> issues;

    public Journal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }

    public BigDecimal getPaperPrice() {
        return paperPrice;
    }

    public void setPaperPrice(BigDecimal paperPrice) {
        this.paperPrice = paperPrice;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public BigDecimal getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(BigDecimal subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }
}
