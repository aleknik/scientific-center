package io.github.aleknik.scientificcenterservice.model.dto;

import java.time.Month;
import java.util.List;

public class IssueDto {

    private long id;

    private int year;

    private Month month;

    private JournalDto journal;

    private List<PaperDto> papers;

    public IssueDto() {
    }


    public IssueDto(long id, int year, Month month) {
        this.id = id;
        this.year = year;
        this.month = month;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public JournalDto getJournal() {
        return journal;
    }

    public void setJournal(JournalDto journal) {
        this.journal = journal;
    }

    public List<PaperDto> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperDto> papers) {
        this.papers = papers;
    }
}
