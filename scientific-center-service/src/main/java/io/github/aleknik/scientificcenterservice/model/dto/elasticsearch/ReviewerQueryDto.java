package io.github.aleknik.scientificcenterservice.model.dto.elasticsearch;

public class ReviewerQueryDto {

    private long paperId;

    private boolean includeScienceField;

    private boolean includeMoreLikeThis;

    private boolean includeDistance;

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public boolean isIncludeScienceField() {
        return includeScienceField;
    }

    public void setIncludeScienceField(boolean includeScienceField) {
        this.includeScienceField = includeScienceField;
    }

    public boolean isIncludeMoreLikeThis() {
        return includeMoreLikeThis;
    }

    public void setIncludeMoreLikeThis(boolean includeMoreLikeThis) {
        this.includeMoreLikeThis = includeMoreLikeThis;
    }

    public boolean isIncludeDistance() {
        return includeDistance;
    }

    public void setIncludeDistance(boolean includeDistance) {
        this.includeDistance = includeDistance;
    }
}
