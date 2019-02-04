package io.github.aleknik.scientificcenterservice.model.elasticsearch;


import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

public class Author {
    @Field(type = FieldType.Keyword, store = true)
    private String externalId;

    @Field(type = FieldType.Text, store = true)
    private String firstName;

    @Field(type = FieldType.Text, store = true)
    private String lastName;

    @GeoPointField
    private GeoPoint location;

    public Author() {
    }

    public Author(String externalId, String firstName, String lastName, double lat, double lon) {
        this.externalId = externalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = new GeoPoint(lat, lon);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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
}
