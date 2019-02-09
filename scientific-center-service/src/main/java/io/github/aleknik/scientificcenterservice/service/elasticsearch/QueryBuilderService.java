package io.github.aleknik.scientificcenterservice.service.elasticsearch;

import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.QueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.QueryOperator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryBuilderService {

    public QueryBuilder build(List<QueryDto> query) {
        final List<QueryBuilder> ors = new ArrayList<>();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        for (QueryDto queryDto : query) {
            if (queryDto.getOperator() == QueryOperator.OR) {
                ors.add(builder);
                builder = QueryBuilders.boolQuery();
            }
            builder.must(build(queryDto));
        }
        ors.add(builder);

        builder = QueryBuilders.boolQuery();
        for (QueryBuilder or : ors) {
            builder.should(or);
        }

        return builder;
    }

    private QueryBuilder build(QueryDto query) {

        QueryBuilder retVal = null;
        if (query.isPhrase()) {
            retVal = QueryBuilders.matchPhrasePrefixQuery(query.getField(), query.getQuery());
        } else {
            retVal = QueryBuilders.termQuery(query.getField(), query.getQuery());
        }

        return retVal;
    }
}
