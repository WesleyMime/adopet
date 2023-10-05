package br.com.adopet.domain.service;

import br.com.adopet.domain.model.PetOwnerEntity;
import br.com.adopet.domain.model.PetOwnerForm;

import java.util.List;
import java.util.UUID;

public interface PetOwnerService {

    List<PetOwnerEntity> getAllPetOwners();

    PetOwnerEntity getPetOwnerById(UUID id);

    PetOwnerEntity postPetOwner(PetOwnerForm PetOwnerForm);

    PetOwnerEntity updatePetOwner(UUID id, PetOwnerForm PetOwnerForm);

    PetOwnerEntity patchPetOwner(UUID id, PetOwnerForm PetOwnerForm);

    boolean deletePetOwner(UUID id);
}
