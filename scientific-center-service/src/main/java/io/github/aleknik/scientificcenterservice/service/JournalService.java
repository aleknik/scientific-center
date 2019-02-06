package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.model.domain.Journal;
import io.github.aleknik.scientificcenterservice.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<Journal> findAll() {
        return journalRepository.findAll();
    }

    public Journal findById(long id) {
        return journalRepository.findById(id).orElseThrow(() -> new NotFoundException("Journal not found"));
    }
}
