package io.github.aleknik.scientificcenterservice.model.dto.elasticsearch;

public class PaperSearchDto {

    private long id;

    private String title;

    private String highlight;

    private boolean openAccess;

    public PaperSearchDto() {
    }

    public PaperSearchDto(long id, String title, String highlight, boolean openAccess) {
        this.id = id;
        this.title = title;
        this.highlight = highlight;
        this.openAccess = openAccess;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }
}
