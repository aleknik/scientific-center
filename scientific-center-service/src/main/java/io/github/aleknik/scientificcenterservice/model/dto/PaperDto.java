package io.github.aleknik.scientificcenterservice.model.dto;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;

import java.math.BigDecimal;

public class PaperDto {

    private long id;

    private String title;

    private String paperAbstract;

    private BigDecimal price;

    private JournalDto journalDto;

    public PaperDto() {
    }

    public PaperDto(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PaperDto(Paper paper) {
        this.id = paper.getId();
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

    public JournalDto getJournalDto() {
        return journalDto;
    }

    public void setJournalDto(JournalDto journalDto) {
        this.journalDto = journalDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
