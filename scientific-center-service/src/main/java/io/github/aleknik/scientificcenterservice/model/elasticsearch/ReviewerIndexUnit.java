package io.github.aleknik.scientificcenterservice.model.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = ReviewerIndexUnit.INDEX_NAME, type = ReviewerIndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class ReviewerIndexUnit {

    public static final String INDEX_NAME = "reviewers";
    public static final String TYPE_NAME = "reviewer";

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
