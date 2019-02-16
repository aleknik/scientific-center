package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class JournalEditor extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    private Journal journal;

    @ManyToOne(fetch = FetchType.EAGER)
    private Editor editor;

    @ManyToOne(fetch = FetchType.EAGER)
    private ScienceField scienceField;

    public JournalEditor() {
    }

    public JournalEditor(Journal journal, Editor editor, ScienceField scienceField) {
        this.journal = journal;
        this.editor = editor;
        this.scienceField = scienceField;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public ScienceField getScienceField() {
        return scienceField;
    }

    public void setScienceField(ScienceField scienceField) {
        this.scienceField = scienceField;
    }
}
