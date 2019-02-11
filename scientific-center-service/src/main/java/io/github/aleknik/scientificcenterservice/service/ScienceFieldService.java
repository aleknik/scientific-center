package io.github.aleknik.scientificcenterservice.service;

import io.github.aleknik.scientificcenterservice.model.domain.ScienceField;
import io.github.aleknik.scientificcenterservice.repository.ScienceFieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScienceFieldService {

    private final ScienceFieldRepository scienceFieldRepository;

    public ScienceFieldService(ScienceFieldRepository scienceFieldRepository) {
        this.scienceFieldRepository = scienceFieldRepository;
    }

    public List<ScienceField> findAll() {
        return scienceFieldRepository.findAll();
    }
}
