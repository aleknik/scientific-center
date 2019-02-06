package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Month;
import java.util.Set;

@Entity
public class Issue extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    private Journal journal;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "issue")
    private Set<Paper> papers;

    private int year;

    private Month month;

    public Issue() {
    }

    public Issue(Journal journal, int year, Month month) {
        this.journal = journal;
        this.year = year;
        this.month = month;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
