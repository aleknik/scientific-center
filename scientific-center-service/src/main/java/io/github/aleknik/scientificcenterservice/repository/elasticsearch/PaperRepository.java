package io.github.aleknik.scientificcenterservice.repository.elasticsearch;

import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperRepository extends ElasticsearchRepository<PaperIndexUnit, String> {
}
