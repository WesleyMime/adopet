package br.com.adopet.domain.controller;

import br.com.adopet.AdopetApplication;
import br.com.adopet.domain.model.PetOwnerEntity;
import br.com.adopet.domain.model.PetOwnerForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.repository.PetOwnerRepository;
import br.com.adopet.domain.service.PetOwnerServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = AdopetApplication.class)
class PetOwnerServiceTest {

    private static final PetOwnerEntity ENTITY = new PetOwnerEntity(UUID.randomUUID(), "test", "test@email.com", "password");

    private static final PetOwnerForm FORM = new PetOwnerForm("test", "test@email.com", "password");

    @InjectMocks
    private PetOwnerServiceImpl service;

    @Mock
    private PetOwnerRepository repository;

    @Mock
    private Validator validator;

    @Test
    void whenGetAllPetOwners_thenReturnAllPetOwners() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));

        List<PetOwnerEntity> list = service.getAllPetOwners();

        assertTrue(list.contains(ENTITY));
    }

    @Test
    void whenGetPetOwnerById_thenReturnPetOwner() {
        UUID id = ENTITY.getId();
        mockFindById();

        PetOwnerEntity PetOwnerById = service.getPetOwnerById(id);

        assertEquals(ENTITY, PetOwnerById);
    }

    @Test
    void whenGetPetOwnerByInvalidId_thenThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> service.getPetOwnerById(UUID.randomUUID()));
    }

    @Test
    void whenPostPetOwner_thenReturnPostedPetOwner() {
        UUID id = ENTITY.getId();
        when(repository.save(any())).thenReturn(ENTITY);

        PetOwnerEntity posted = service.postPetOwner(FORM);

        assertEquals(ENTITY.getId(), posted.getId());
        assertEquals(ENTITY.getName(), posted.getName());
        assertEquals(ENTITY.getEmail(), posted.getEmail());
        assertEquals(ENTITY.getPassword(), posted.getPassword());
    }

    @Test
    void whenPostPetOwnerWithSameEmail_thenThrowException() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(ENTITY));

        assertThrows(EmailAlreadyRegisteredException.class, () -> service.postPetOwner(FORM));
    }

    @Test
    void whenUpdatePetOwner_thenReturnUpdatedPetOwner() {
        UUID id = ENTITY.getId();
        mockFindById();

        PetOwnerForm PetOwnerForm = new PetOwnerForm("updated", "updated@email.com", "updated");
        PetOwnerEntity updated = service.updatePetOwner(id, PetOwnerForm);

        assertEquals(id, updated.getId());
        assertEquals(PetOwnerForm.getName(), updated.getName());
        assertEquals(PetOwnerForm.getEmail(), updated.getEmail());
        assertEquals(PetOwnerForm.getPassword(), updated.getPassword());
    }

    @Test
    void whenPatchPetOwner_thenReturnPatchedPetOwner() {
        UUID id = ENTITY.getId();
        String newName = "newName";
        mockFindById();
        when(repository.findByEmail(any())).thenReturn(Optional.of(ENTITY));

        PetOwnerForm PetOwnerForm = new PetOwnerForm(newName, null, null);
        PetOwnerEntity patched = service.patchPetOwner(id, PetOwnerForm);

        assertEquals(id, patched.getId());
        assertEquals(PetOwnerForm.getName(), patched.getName());
        assertEquals(ENTITY.getEmail(), patched.getEmail());
        assertEquals(ENTITY.getPassword(), patched.getPassword());
    }

    @Test
    void whenDeletePetOwner_thenReturnTrue() {
        UUID id = ENTITY.getId();
        mockFindById();

        boolean deleted = service.deletePetOwner(id);
        assertTrue(deleted);
    }

    private void mockFindById() {
        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
    }
}