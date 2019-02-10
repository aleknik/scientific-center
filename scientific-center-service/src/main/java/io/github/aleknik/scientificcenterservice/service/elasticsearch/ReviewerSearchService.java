package io.github.aleknik.scientificcenterservice.service.elasticsearch;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.ReviewerIndexUnit;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESReviewerRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class ReviewerSearchService {
    private final ESReviewerRepository esReviewerRepository;
    private final ESPaperRepository esPaperRepository;
    private final UserRepository userRepository;

    public ReviewerSearchService(ESReviewerRepository esReviewerRepository, ESPaperRepository esPaperRepository, UserRepository userRepository) {
        this.esReviewerRepository = esReviewerRepository;
        this.esPaperRepository = esPaperRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewerSearchDto> searchByJournalAndField(long journalId, long fieldId) {
        final QueryBuilder journalQuery = nestedQuery("journals", boolQuery().must(termQuery("journals.externalId", journalId)), ScoreMode.None);

        final QueryBuilder fieldQuery = nestedQuery("scienceFields", boolQuery().must(termQuery("scienceFields.externalId", fieldId)), ScoreMode.None);

        final BoolQueryBuilder query = boolQuery().must(journalQuery).must(fieldQuery);

        final Iterable<ReviewerIndexUnit> search = esReviewerRepository.search(query);

        final List<ReviewerSearchDto> dtos = new ArrayList<>();
        for (ReviewerIndexUnit reviewerIndexUnit : search) {
            dtos.add(new ReviewerSearchDto(Long.parseLong(reviewerIndexUnit.getExternalId()), reviewerIndexUnit.getFirstName(), reviewerIndexUnit.getLastName()));
        }

        return dtos;
    }

    public List<ReviewerSearchDto> searchByJournalAndLikePaper(long journalId, String text) {

        final MoreLikeThisQueryBuilder moreLikeThisQuery = moreLikeThisQuery(new String[]{"content"}, new String[]{"text"}, null);
        moreLikeThisQuery.minDocFreq(1);
        moreLikeThisQuery.minTermFreq(1);


        final BoolQueryBuilder query = boolQuery().must(moreLikeThisQuery);
        final Iterable<PaperIndexUnit> search = esPaperRepository.search(query);

        return null;
    }

    public void IndexReviewer(long id) {
        final Reviewer reviewer = (Reviewer) userRepository.findById(id).orElseThrow(() -> new BadRequestException("Reviewer not found"));

        esReviewerRepository.index(new ReviewerIndexUnit(reviewer));
    }
}
