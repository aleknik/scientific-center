package io.github.aleknik.scientificcenterservice.model.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;
import java.util.List;

@Document(indexName = PaperIndexUnit.INDEX_NAME, type = PaperIndexUnit.TYPE_NAME, shards = 1, replicas = 0)
@Setting(settingPath = "/settings/settings.json")
public class PaperIndexUnit {

    public static final String INDEX_NAME = "papers";
    public static final String TYPE_NAME = "paper";


    @Id
    private String id;

    @Field(type = FieldType.Text, store = true)
    private String title;

    @Field(type = FieldType.Keyword, store = true)
    private String externalId;

    @Field(type = FieldType.Text, store = true)
    private String journal;

    @Field(type = FieldType.Text, store = true)
    private List<String> keywords;

    @Field(type = FieldType.Text, store = true)
    private List<String> authorNames;

    @Field(type = FieldType.Boolean, store = true)
    private boolean openAccess;

    @Field(type = FieldType.Nested, store = true)
    private List<Author> authors;

    @Field(type = FieldType.Nested, store = true)
    private List<ReviewerIndexUnit> reviewers;

    @Field(type = FieldType.Text, store = true)
    private String content;

    @Field(type = FieldType.Text, store = true)
    private String scienceField;

    private String highlight;

    public PaperIndexUnit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }

    public List<ReviewerIndexUnit> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<ReviewerIndexUnit> reviewers) {
        this.reviewers = reviewers;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getScienceField() {
        return scienceField;
    }

    public void setScienceField(String scienceField) {
        this.scienceField = scienceField;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }
}
