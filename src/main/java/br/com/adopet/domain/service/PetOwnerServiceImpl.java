package br.com.adopet.domain.service;

import br.com.adopet.domain.model.PetOwnerEntity;
import br.com.adopet.domain.model.PetOwnerForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.repository.PetOwnerRepository;
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
public class PetOwnerServiceImpl implements PetOwnerService{

    private PetOwnerRepository PetOwnerRepository;

    private Validator validator;

    public List<PetOwnerEntity> getAllPetOwners() {
        return PetOwnerRepository.findAll();
    }

    public PetOwnerEntity getPetOwnerById(UUID id) {
        Optional<PetOwnerEntity> PetOwnerEntityOptional = PetOwnerRepository.findById(id);
        if (PetOwnerEntityOptional.isEmpty()) throw new ResourceNotFoundException("PetOwner with id " + id + " was not found.");
        return PetOwnerEntityOptional.get();
    }

    public PetOwnerEntity postPetOwner(PetOwnerForm PetOwnerForm) {
        PetOwnerEntity PetOwnerEntity = new PetOwnerEntity(PetOwnerForm.getName(), PetOwnerForm.getEmail(), PetOwnerForm.getPassword());
        validatePetOwnerForm(PetOwnerEntity.getId(), PetOwnerForm);

        return PetOwnerRepository.save(PetOwnerEntity);
    }

    public PetOwnerEntity updatePetOwner(UUID id, PetOwnerForm PetOwnerForm) {
        PetOwnerEntity PetOwnerEntity = getPetOwnerById(id);
        validatePetOwnerForm(id, PetOwnerForm);

        PetOwnerEntity.setName(PetOwnerForm.getName());
        PetOwnerEntity.setEmail(PetOwnerForm.getEmail());
        PetOwnerEntity.setPassword(PetOwnerForm.getPassword());

        return PetOwnerEntity;
    }

    public PetOwnerEntity patchPetOwner(UUID id, PetOwnerForm PetOwnerForm) {
        PetOwnerEntity PetOwnerEntity = getPetOwnerById(id);
        validatePetOwnerForm(id, PetOwnerForm);

        if (PetOwnerForm.getName() != null) PetOwnerEntity.setName(PetOwnerForm.getName());
        if (PetOwnerForm.getEmail() != null) PetOwnerEntity.setEmail(PetOwnerForm.getEmail());
        if (PetOwnerForm.getPassword() != null) PetOwnerEntity.setPassword(PetOwnerForm.getPassword());

        return PetOwnerEntity;
    }

    public boolean deletePetOwner(UUID id) {
        getPetOwnerById(id);
        PetOwnerRepository.deleteById(id);
        return true;
    }

    void validatePetOwnerForm(UUID id, PetOwnerForm form) {
        Set<ConstraintViolation<PetOwnerForm>> violations = validator.validate(form);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        Optional<PetOwnerEntity> PetOwnerWithEmail = PetOwnerRepository.findByEmail(form.getEmail());

        if (PetOwnerWithEmail.isPresent()) {
            PetOwnerEntity PetOwnerEntity = PetOwnerWithEmail.get();
            if (!PetOwnerEntity.getId().equals(id)) {
                throw new EmailAlreadyRegisteredException();
            }
        }

    }
}
