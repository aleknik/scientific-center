package io.github.aleknik.scientificcenterservice.model.dto.elasticsearch;

public class PaperQueryDto {

    private String query;

    private boolean phrase;

    private QueryOperator operator;

    private String field;

    public PaperQueryDto() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isPhrase() {
        return phrase;
    }

    public void setPhrase(boolean phrase) {
        this.phrase = phrase;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public void setOperator(QueryOperator operator) {
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
