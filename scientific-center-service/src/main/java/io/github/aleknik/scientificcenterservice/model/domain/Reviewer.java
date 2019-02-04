package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Reviewer extends User {

    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ScienceField> scienceFields;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Journal> journals;
}
