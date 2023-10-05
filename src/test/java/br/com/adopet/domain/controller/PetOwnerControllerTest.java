package br.com.adopet.domain.controller;

import br.com.adopet.AdopetApplication;
import br.com.adopet.domain.model.PetOwnerEntity;
import br.com.adopet.domain.model.PetOwnerForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.service.PetOwnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = AdopetApplication.class)
class PetOwnerControllerTest {

    private static final PetOwnerEntity ENTITY = new PetOwnerEntity(UUID.randomUUID(), "test", "test@email.com", "password");

    private static final PetOwnerForm FORM = new PetOwnerForm("test", "test@email.com", "password");

    private static final String TUTORES_PATH = "/tutores/";

    @Mock
    private PetOwnerServiceImpl service;

    @InjectMocks
    private PetOwnerController controller;


    @Test
    void whenGetAllPetOwners_thenReturnOKAllPetOwners() {
        when(service.getAllPetOwners()).thenReturn(List.of(ENTITY));

        ResponseEntity<List<PetOwnerEntity>> responseEntity = controller.getAllPetOwners();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(ENTITY));
    }

    @Test
    void whenGetPetOwnerById_thenReturnOkAndPetOwner() {
        UUID id = ENTITY.getId();
        when(service.getPetOwnerById(id)).thenReturn(ENTITY);

        ResponseEntity<PetOwnerEntity> responseEntity = controller.getPetOwnerById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenGetPetOwnerByInvalidId_thenReturnNotFoundAndExceptionMessage() {
        UUID id = UUID.randomUUID();
        when(service.getPetOwnerById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> controller.getPetOwnerById(id));
    }

    @Test
    void whenPostPetOwner_thenReturnCreatedAndPostedPetOwner() {
        UUID id = ENTITY.getId();
        when(service.postPetOwner(any())).thenReturn(ENTITY);

        ResponseEntity<PetOwnerEntity> responseEntity = controller.postPetOwner(FORM);

        assertEquals( HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(TUTORES_PATH + id, responseEntity.getHeaders().getLocation().getPath());

        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenPostPetOwnerWithSameEmail_thenReturnBadRequestAndExceptionMessage() {
        when(service.postPetOwner(any())).thenThrow(EmailAlreadyRegisteredException.class);

        assertThrows(EmailAlreadyRegisteredException.class, () -> controller.postPetOwner(FORM));
    }

    @Test
    void whenUpdatePetOwner_thenReturnOKAndUpdatedPetOwner() {
        UUID id = ENTITY.getId();

        when(service.updatePetOwner(id, FORM)).thenReturn(ENTITY);
        ResponseEntity<PetOwnerEntity> responseEntity = controller.updatePetOwner(id, FORM);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenPatchPetOwner_thenReturnOKAndPatchedPetOwner() {
        UUID id = ENTITY.getId();

        when(service.patchPetOwner(id, FORM)).thenReturn(ENTITY);
        ResponseEntity<PetOwnerEntity> responseEntity = controller.patchPetOwner(id, FORM);

        assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenDeletePetOwner_thenReturnNoContent() {
        UUID id = ENTITY.getId();
        when(service.getPetOwnerById(id)).thenReturn(ENTITY);

        ResponseEntity<PetOwnerEntity> responseEntity = controller.deletePetOwner(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}