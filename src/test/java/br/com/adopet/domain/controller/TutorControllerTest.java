package br.com.adopet.domain.controller;

import br.com.adopet.AdopetApplication;
import br.com.adopet.domain.model.TutorEntity;
import br.com.adopet.domain.model.TutorForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.service.TutorServiceImpl;
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
class TutorControllerTest {

    private static final TutorEntity ENTITY = new TutorEntity(UUID.randomUUID(), "test", "test@email.com", "password");

    private static final TutorForm FORM = new TutorForm("test", "test@email.com", "password");

    private static final String TUTORES_PATH = "/tutores/";

    @Mock
    private TutorServiceImpl service;

    @InjectMocks
    private TutorController controller;


    @Test
    void whenGetAllTutors_thenReturnOKAllTutores() {
        when(service.getAllTutores()).thenReturn(List.of(ENTITY));

        ResponseEntity<List<TutorEntity>> responseEntity = controller.getAllTutores();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(ENTITY));
    }

    @Test
    void whenGetTutorById_thenReturnOkAndTutor() {
        UUID id = ENTITY.getId();
        when(service.getTutorById(id)).thenReturn(ENTITY);

        ResponseEntity<TutorEntity> responseEntity = controller.getTutorById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenGetTutorByInvalidId_thenReturnNotFoundAndExceptionMessage() {
        UUID id = UUID.randomUUID();
        when(service.getTutorById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> controller.getTutorById(id));
    }

    @Test
    void whenPostTutor_thenReturnCreatedAndPostedTutor() {
        UUID id = ENTITY.getId();
        when(service.postTutor(any())).thenReturn(ENTITY);

        ResponseEntity<TutorEntity> responseEntity = controller.postTutor(FORM);

        assertEquals( HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(TUTORES_PATH + id, responseEntity.getHeaders().getLocation().getPath());

        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenPostTutorWithSameEmail_thenReturnBadRequestAndExceptionMessage() {
        when(service.postTutor(any())).thenThrow(EmailAlreadyRegisteredException.class);

        assertThrows(EmailAlreadyRegisteredException.class, () -> controller.postTutor(FORM));
    }

    @Test
    void whenUpdateTutor_thenReturnOKAndUpdatedTutor() {
        UUID id = ENTITY.getId();

        when(service.updateTutor(id, FORM)).thenReturn(ENTITY);
        ResponseEntity<TutorEntity> responseEntity = controller.updateTutor(id, FORM);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenPatchTutor_thenReturnOKAndPatchedTutor() {
        UUID id = ENTITY.getId();

        when(service.patchTutor(id, FORM)).thenReturn(ENTITY);
        ResponseEntity<TutorEntity> responseEntity = controller.patchTutor(id, FORM);

        assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), ENTITY);
    }

    @Test
    void whenDeleteTutor_thenReturnNoContent() {
        UUID id = ENTITY.getId();
        when(service.getTutorById(id)).thenReturn(ENTITY);

        ResponseEntity<TutorEntity> responseEntity = controller.deleteTutor(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}