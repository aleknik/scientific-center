package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;

@Entity
public class Author extends User {

    public Author(String email, String password, String firstName, String lastName, Address address) {
        super(email, password, firstName, lastName, address);
    }

    public Author() {
    }
}
