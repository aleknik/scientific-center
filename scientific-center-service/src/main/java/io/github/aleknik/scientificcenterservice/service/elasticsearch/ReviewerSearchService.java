package io.github.aleknik.scientificcenterservice.service.elasticsearch;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.model.domain.Address;
import io.github.aleknik.scientificcenterservice.model.domain.Paper;
import io.github.aleknik.scientificcenterservice.model.domain.Reviewer;
import io.github.aleknik.scientificcenterservice.model.domain.UnregisteredAuthor;
import io.github.aleknik.scientificcenterservice.model.dto.elasticsearch.ReviewerSearchDto;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.PaperIndexUnit;
import io.github.aleknik.scientificcenterservice.model.elasticsearch.ReviewerIndexUnit;
import io.github.aleknik.scientificcenterservice.repository.UserRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESPaperRepository;
import io.github.aleknik.scientificcenterservice.repository.elasticsearch.ESReviewerRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        return search(query);
    }

    private List<ReviewerSearchDto> search(QueryBuilder query) {
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


        final Iterable<PaperIndexUnit> results = esPaperRepository.search(moreLikeThisQuery);

        final List<PaperIndexUnit> papers = new ArrayList<>();
        results.forEach(papers::add);

        final String id = String.valueOf(journalId);
        return papers.stream()
                .flatMap(p -> p.getReviewers().stream())
                .filter(r -> r.getJournals().stream().anyMatch(j -> j.getExternalId().equals(id)))
                .map(r -> new ReviewerSearchDto(Long.parseLong(r.getExternalId()), r.getFirstName(), r.getLastName()))
                .collect(Collectors.toList());
    }

    public List<ReviewerSearchDto> searchByJournalAndDistance(Paper paper, double distance) {

        List<Address> addresses = new ArrayList<>();

        addresses.add(paper.getAuthor().getAddress());

        for (UnregisteredAuthor coauthor : paper.getCoauthors()) {
            addresses.add(coauthor.getAddress());
        }

        final BoolQueryBuilder query = boolQuery();
        for (Address address : addresses) {
            query.mustNot(geoDistanceQuery("location")
                    .distance(distance, DistanceUnit.KILOMETERS)
                    .point(address.getLatitude(), address.getLongitude()));
        }

        return search(query);

    }



    public void IndexReviewer(long id) {
        final Reviewer reviewer = (Reviewer) userRepository.findById(id).orElseThrow(() -> new BadRequestException("Reviewer not found"));

        esReviewerRepository.index(new ReviewerIndexUnit(reviewer));
    }
}
