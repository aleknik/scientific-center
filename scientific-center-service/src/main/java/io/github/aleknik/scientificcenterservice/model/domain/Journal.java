package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;

@Entity
public class Journal extends BaseModel {

    private String name;

    public Journal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
