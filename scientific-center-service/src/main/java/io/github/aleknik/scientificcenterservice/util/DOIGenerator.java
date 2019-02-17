package io.github.aleknik.scientificcenterservice.util;

import io.github.aleknik.scientificcenterservice.model.domain.Paper;

public class DOIGenerator {

    public static String generateForPaper(Paper paper) {

        final long paperId = paper.getId();
        final long journalId = paper.getJournal().getId();

        return String.format("%d/%d", journalId, paperId);
    }
}
