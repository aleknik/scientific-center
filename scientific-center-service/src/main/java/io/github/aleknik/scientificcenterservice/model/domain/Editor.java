package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "editor")
public class Editor extends User {

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Journal journal;

    public Editor() {
    }

    public Editor(String email, String username, String password, String firstName, String lastName, Address address) {
        super(email, username, password, firstName, lastName, address);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
