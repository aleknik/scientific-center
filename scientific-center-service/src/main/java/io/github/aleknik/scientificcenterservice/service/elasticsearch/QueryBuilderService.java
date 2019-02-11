package io.github.aleknik.scientificcenterservice.service.elasticsearch;

import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperQueryDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.QueryOperator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class QueryBuilderService {

    public QueryBuilder build(List<PaperQueryDto> query) {
        final List<List<QueryBuilder>> ors = new ArrayList<>();
        List<QueryBuilder> ands = new ArrayList<>();

        for (PaperQueryDto paperQueryDto : query) {
            if (paperQueryDto.getOperator() == QueryOperator.OR) {
                ors.add(ands);
                ands = new ArrayList<>();
            }
            ands.add(build(paperQueryDto));
        }
        ors.add(ands);

        BoolQueryBuilder builder;
        List<QueryBuilder> orBuilders = new ArrayList<>();
        for (List<QueryBuilder> or : ors) {
            builder = QueryBuilders.boolQuery();
            if (or.size() == 1) {
                orBuilders.add(builder.should(or.get(0)));
            } else {
                for (QueryBuilder and : or) {
                    builder.must(and);
                }
                orBuilders.add(builder);
            }
        }

        builder = QueryBuilders.boolQuery();
        for (QueryBuilder or : orBuilders) {
            builder.should(or);
        }
        return builder;
    }

    private QueryBuilder build(PaperQueryDto query) {

        QueryBuilder retVal = null;
        if (query.isPhrase()) {
            retVal = QueryBuilders.matchPhraseQuery(query.getField(), query.getQuery()).analyzer("serbian");
        } else {
            retVal = QueryBuilders.matchQuery(query.getField(), query.getQuery()).analyzer("serbian");
        }

        return retVal;
    }
}
