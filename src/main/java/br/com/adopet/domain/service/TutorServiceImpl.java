package br.com.adopet.domain.service;

import br.com.adopet.domain.model.TutorEntity;
import br.com.adopet.domain.model.TutorForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.repository.TutorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TutorServiceImpl implements TutorService {

    private TutorRepository TutorRepository;

    private Validator validator;

    public List<TutorEntity> getAllTutores() {
        return TutorRepository.findAll();
    }

    public TutorEntity getTutorById(UUID id) {
        Optional<TutorEntity> TutorEntityOptional = TutorRepository.findById(id);
        if (TutorEntityOptional.isEmpty())
            throw new ResourceNotFoundException("Tutor com id " + id + " não encontrado.");
        return TutorEntityOptional.get();
    }

    public TutorEntity postTutor(TutorForm TutorForm) {
        TutorEntity TutorEntity = new TutorEntity(TutorForm.getName(), TutorForm.getEmail(), TutorForm.getPassword());
        validateTutorForm(TutorEntity.getId(), TutorForm);

        return TutorRepository.save(TutorEntity);
    }

    public TutorEntity updateTutor(UUID id, TutorForm TutorForm) {
        TutorEntity TutorEntity = getTutorById(id);
        validateTutorForm(id, TutorForm);

        TutorEntity.setName(TutorForm.getName());
        TutorEntity.setEmail(TutorForm.getEmail());
        TutorEntity.setPassword(TutorForm.getPassword());

        return TutorEntity;
    }

    public TutorEntity patchTutor(UUID id, TutorForm TutorForm) {
        TutorEntity TutorEntity = getTutorById(id);
        validateTutorForm(id, TutorForm);

        if (TutorForm.getName() != null) TutorEntity.setName(TutorForm.getName());
        if (TutorForm.getEmail() != null) TutorEntity.setEmail(TutorForm.getEmail());
        if (TutorForm.getPassword() != null) TutorEntity.setPassword(TutorForm.getPassword());

        return TutorEntity;
    }

    public boolean deleteTutor(UUID id) {
        getTutorById(id);
        TutorRepository.deleteById(id);
        return true;
    }

    void validateTutorForm(UUID id, TutorForm form) {
        Set<ConstraintViolation<TutorForm>> violations = validator.validate(form);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        Optional<TutorEntity> TutorWithEmail = TutorRepository.findByEmail(form.getEmail());

        if (TutorWithEmail.isPresent()) {
            TutorEntity TutorEntity = TutorWithEmail.get();
            if (!TutorEntity.getId().equals(id)) {
                throw new EmailAlreadyRegisteredException();
            }
        }

    }
}
