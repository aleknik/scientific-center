package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class UnregisteredAuthor extends BaseModel {

    private String firstName;

    private String lastName;

    @Embedded
    private Address address;

    private String email;

    public UnregisteredAuthor() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
