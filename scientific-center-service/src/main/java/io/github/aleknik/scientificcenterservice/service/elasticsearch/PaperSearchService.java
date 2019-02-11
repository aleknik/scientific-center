package io.github.aleknik.scientificcenterservice.service.elasticsearch;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.handler.PDFHandler;
import io.github.aleknik.scientificcenterservice.model.domain.Keyword;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperSearchDto;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.PaperQueryDto;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.Author;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.ReviewerIndexUnit;
import io.github.aleknik.scientificcenterservice.repository.PaperRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaperSearchService {

    private final PDFHandler pdfHandler;
    private final ESPaperRepository esPaperRepository;
    private final StorageService storageService;
    private final PaperRepository paperRepository;
    private final QueryBuilderService queryBuilderService;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public PaperSearchService(PDFHandler pdfHandler,
                              ESPaperRepository esPaperRepository,
                              StorageService storageService,
                              PaperRepository paperRepository,
                              QueryBuilderService queryBuilderService,
                              ElasticsearchTemplate elasticsearchTemplate) {
        this.pdfHandler = pdfHandler;
        this.esPaperRepository = esPaperRepository;
        this.storageService = storageService;
        this.paperRepository = paperRepository;
        this.queryBuilderService = queryBuilderService;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public String getContent(long id) {
        final byte[] data = storageService.load(String.valueOf(id));
        return pdfHandler.getText(data);

    }

    public void indexPaper(long id) {
        final Paper paper = paperRepository.findById(id).orElseThrow(() -> new BadRequestException("Paper not found"));
        final String text = getContent(id);


        esPaperRepository.index(convertPaperToIndexUnit(paper, text));
    }

    private PaperIndexUnit convertPaperToIndexUnit(Paper paper, String text) {

        final PaperIndexUnit paperIndexUnit = new PaperIndexUnit();

        paperIndexUnit.setOpenAccess(paper.getJournal().isOpenAccess());
        paperIndexUnit.setKeywords(paper.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()));
        paperIndexUnit.setExternalId(String.valueOf(paper.getId()));
        paperIndexUnit.setTitle(paper.getTitle());
        paperIndexUnit.setJournal(paper.getJournal().getName());
        paperIndexUnit.setScienceField(paper.getScienceField().getName());
        List<Author> authors = new ArrayList<>();
        final Author author = new Author(String.valueOf(paper.getAuthor().getId()),
                paper.getAuthor().getFirstName(),
                paper.getAuthor().getLastName(),
                paper.getAuthor().getAddress().getLatitude(),
                paper.getAuthor().getAddress().getLongitude());

        authors.add(author);

        paperIndexUnit.setAuthorNames(authors.stream()
                .map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toList()));

        authors.addAll(paper.getCoauthors().stream()
                .map(ca -> new Author(null, ca.getFirstName(),
                        ca.getLastName(),
                        ca.getAddress().getLatitude(),
                        ca.getAddress().getLongitude()))
                .collect(Collectors.toList()));

        paperIndexUnit.setAuthors(authors);
        paperIndexUnit.setContent(text);
        paperIndexUnit.setReviewers(paper.getReviewers().stream().map(ReviewerIndexUnit::new).collect(Collectors.toList()));

        return paperIndexUnit;
    }

    public List<PaperSearchDto> search(List<PaperQueryDto> query, List<String> highlightFields) {
        final QueryBuilder builder = queryBuilderService.build(query);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightFields(highlightFields.stream().map(HighlightBuilder.Field::new).toArray(HighlightBuilder.Field[]::new))
                .build();

        final AggregatedPage<PaperIndexUnit> results = elasticsearchTemplate.queryForPage(searchQuery, PaperIndexUnit.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                List<PaperIndexUnit> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    final Map<String, Object> map = searchHit.getSourceAsMap();
                    PaperIndexUnit paper = new PaperIndexUnit();
                    paper.setExternalId(String.valueOf(map.get("externalId")));
                    paper.setTitle((String) map.get("title"));
                    paper.setOpenAccess((Boolean) map.get("openAccess"));
                    final String highlight = searchHit.getHighlightFields().values().stream()
                            .flatMap(f -> Arrays.stream(f.fragments()))
                            .map(Text::toString)
                            .collect(Collectors.joining("..."));
                    paper.setHighlight(highlight);
                    chunk.add(paper);
                }
                if (chunk.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) chunk);
                }
                return null;
            }
        });


        return results != null ? results.stream()
                .map(res -> new PaperSearchDto(Long.parseLong(res.getExternalId()),
                        res.getTitle(), res.getHighlight(), res.isOpenAccess()))
                .collect(Collectors.toList()) :
                new ArrayList<>();
    }

}
