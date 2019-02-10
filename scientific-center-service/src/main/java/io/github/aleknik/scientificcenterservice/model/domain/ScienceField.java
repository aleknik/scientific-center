package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;

@Entity
public class ScienceField extends BaseModel {

    public String getName() {
        return name;
    }

    public ScienceField() {
    }

    public ScienceField(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
