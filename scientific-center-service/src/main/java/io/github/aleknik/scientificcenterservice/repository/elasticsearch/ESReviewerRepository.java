package io.github.aleknik.scientificcenterservice.repository.elasticsearch;

import io.github.aleknik.scientificcenterservice.model.elasticsearch.ReviewerIndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESReviewerRepository extends ElasticsearchRepository<ReviewerIndexUnit, String> {
}
