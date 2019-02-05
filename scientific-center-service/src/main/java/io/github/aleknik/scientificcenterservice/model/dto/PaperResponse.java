package io.github.aleknik.scientificcenterservice.model.dto;

import java.math.BigDecimal;

public class PaperResponse {
    private String title;

    private String paperAbstract;

    private BigDecimal price;

    public PaperResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
