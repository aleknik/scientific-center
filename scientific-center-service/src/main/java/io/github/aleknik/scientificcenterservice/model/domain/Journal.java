package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;

@Entity
public class Journal extends BaseModel {

    private String name;

    private boolean openAccess;

    public Journal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }
}
