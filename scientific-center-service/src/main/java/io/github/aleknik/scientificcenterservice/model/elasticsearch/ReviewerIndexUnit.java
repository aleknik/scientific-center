package io.github.aleknik.scientificcenterservice.model.elasticsearch;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;

@Document(indexName = ReviewerIndexUnit.INDEX_NAME, type = ReviewerIndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class ReviewerIndexUnit {

    public static final String INDEX_NAME = "reviewers";
    public static final String TYPE_NAME = "reviewer";

    @Id
    private String id;

    @Field(type = FieldType.Keyword, store = true)
    private String externalId;

    @Field(type = FieldType.Text, store = true)
    private String firstName;

    @Field(type = FieldType.Text, store = true)
    private String lastName;

    @Field(type = FieldType.Text, store = true)
    private String journal;

    @GeoPointField
    private GeoPoint location;

    public ReviewerIndexUnit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getIndexName() {
        return INDEX_NAME;
    }

    public static String getTypeName() {
        return TYPE_NAME;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
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
}