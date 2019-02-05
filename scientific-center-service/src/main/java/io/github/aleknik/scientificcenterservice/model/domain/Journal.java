package io.github.aleknik.scientificcenterservice.model.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "journal")
public class Journal extends BaseModel {

    private String name;

    private boolean openAccess;

    private BigDecimal paperPrice;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "journal")
    private Editor editor;

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

    public BigDecimal getPaperPrice() {
        return paperPrice;
    }

    public void setPaperPrice(BigDecimal paperPrice) {
        this.paperPrice = paperPrice;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}
