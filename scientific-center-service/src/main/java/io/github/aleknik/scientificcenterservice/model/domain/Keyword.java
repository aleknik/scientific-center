package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;

@Entity
public class Keyword extends BaseModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
