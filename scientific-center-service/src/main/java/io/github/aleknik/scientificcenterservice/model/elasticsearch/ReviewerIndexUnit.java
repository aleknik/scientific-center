package io.github.aleknik.scientificcenterservice.model.elasticsearch;

import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

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

    @Field(type = FieldType.Nested, store = true)
    private List<Journal> journals;

    @Field(type = FieldType.Nested, store = true)
    private List<ScienceField> scienceFields;

    @GeoPointField
    private GeoPoint location;

    public ReviewerIndexUnit() {
    }

    public ReviewerIndexUnit(Reviewer reviewer) {
        this.setExternalId(String.valueOf(reviewer.getId()));
        this.setFirstName(reviewer.getFirstName());
        this.setLastName(reviewer.getLastName());
        this.setJournals(reviewer.getJournals().stream().map(j ->
                new Journal(String.valueOf(j.getId()), j.getName())).collect(Collectors.toList()));

        this.setScienceFields(reviewer.getScienceFields().stream().map(sf ->
                new ScienceField(String.valueOf(sf.getId()), sf.getName())).collect(Collectors.toList()));

        this.setLocation(new GeoPoint(reviewer.getAddress().getLatitude(), reviewer.getAddress().getLongitude()));
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


    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    public List<ScienceField> getScienceFields() {
        return scienceFields;
    }

    public void setScienceFields(List<ScienceField> scienceFields) {
        this.scienceFields = scienceFields;
    }
}
