package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reviewer extends User {

    private String title;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "reviewer_science_field",
            joinColumns = @JoinColumn(name = "reviewer_id"),
            inverseJoinColumns = @JoinColumn(name = "science_field_id")
    )
    private Set<ScienceField> scienceFields = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "reviewer_journal",
            joinColumns = @JoinColumn(name = "reviewer_id"),
            inverseJoinColumns = @JoinColumn(name = "journal_id")
    )
    private Set<Journal> journals = new HashSet<>();

    public Reviewer() {
    }

    public Reviewer(String email, String username, String password, String firstName, String lastName, Address address) {
        super(email, username, password, firstName, lastName, address);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ScienceField> getScienceFields() {
        return scienceFields;
    }

    public void setScienceFields(Set<ScienceField> scienceFields) {
        this.scienceFields = scienceFields;
    }

    public Set<Journal> getJournals() {
        return journals;
    }

    public void setJournals(Set<Journal> journals) {
        this.journals = journals;
    }
}
