package io.github.aleknik.scientificcenterservice.model.elasticsearch;

public class Reviewer {

    private String externalId;

    private String firstName;

    private String lastName;

    public Reviewer() {
    }

    public Reviewer(String externalId, String firstName, String lastName) {
        this.externalId = externalId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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
}
