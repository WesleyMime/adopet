package br.com.adopet.domain.service;

import br.com.adopet.domain.model.TutorEntity;
import br.com.adopet.domain.model.TutorForm;

import java.util.List;
import java.util.UUID;

public interface TutorService {

    List<TutorEntity> getAllTutores();

    TutorEntity getTutorById(UUID id);

    TutorEntity postTutor(TutorForm TutorForm);

    TutorEntity updateTutor(UUID id, TutorForm TutorForm);

    TutorEntity patchTutor(UUID id, TutorForm TutorForm);

    boolean deleteTutor(UUID id);
}
