package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Editor extends User {

    private String title;

    @OneToOne(fetch = FetchType.EAGER)
    private Journal jurnal;

    public Editor() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Journal getJurnal() {
        return jurnal;
    }

    public void setJurnal(Journal jurnal) {
        this.jurnal = jurnal;
    }
}
