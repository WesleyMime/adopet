package br.com.adopet.domain.controller;

import br.com.adopet.AdopetApplication;
import br.com.adopet.domain.model.TutorEntity;
import br.com.adopet.domain.model.TutorForm;
import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import br.com.adopet.domain.repository.TutorRepository;
import br.com.adopet.domain.service.TutorServiceImpl;
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
class TutorServiceTest {

    private static final TutorEntity ENTITY = new TutorEntity(UUID.randomUUID(), "test", "test@email.com", "password");

    private static final TutorForm FORM = new TutorForm("test", "test@email.com", "password");

    @InjectMocks
    private TutorServiceImpl service;

    @Mock
    private TutorRepository repository;

    @Mock
    private Validator validator;

    @Test
    void whenGetAllTutors_thenReturnAllTutors() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));

        List<TutorEntity> list = service.getAllTutores();

        assertTrue(list.contains(ENTITY));
    }

    @Test
    void whenGetTutorById_thenReturnTutor() {
        UUID id = ENTITY.getId();
        mockFindById();

        TutorEntity TutorById = service.getTutorById(id);

        assertEquals(ENTITY, TutorById);
    }

    @Test
    void whenGetTutorByInvalidId_thenThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> service.getTutorById(UUID.randomUUID()));
    }

    @Test
    void whenPostTutor_thenReturnPostedTutor() {
        when(repository.save(any())).thenReturn(ENTITY);

        TutorEntity posted = service.postTutor(FORM);

        assertEquals(ENTITY.getId(), posted.getId());
        assertEquals(ENTITY.getName(), posted.getName());
        assertEquals(ENTITY.getEmail(), posted.getEmail());
        assertEquals(ENTITY.getPassword(), posted.getPassword());
    }

    @Test
    void whenPostTutorWithSameEmail_thenThrowException() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(ENTITY));

        assertThrows(EmailAlreadyRegisteredException.class, () -> service.postTutor(FORM));
    }

    @Test
    void whenUpdateTutor_thenReturnUpdatedTutor() {
        UUID id = ENTITY.getId();
        mockFindById();

        TutorForm TutorForm = new TutorForm("updated", "updated@email.com", "updated");
        TutorEntity updated = service.updateTutor(id, TutorForm);

        assertEquals(id, updated.getId());
        assertEquals(TutorForm.getName(), updated.getName());
        assertEquals(TutorForm.getEmail(), updated.getEmail());
        assertEquals(TutorForm.getPassword(), updated.getPassword());
    }

    @Test
    void whenPatchTutor_thenReturnPatchedTutor() {
        UUID id = ENTITY.getId();
        String newName = "newName";
        mockFindById();
        when(repository.findByEmail(any())).thenReturn(Optional.of(ENTITY));

        TutorForm TutorForm = new TutorForm(newName, null, null);
        TutorEntity patched = service.patchTutor(id, TutorForm);

        assertEquals(id, patched.getId());
        assertEquals(TutorForm.getName(), patched.getName());
        assertEquals(ENTITY.getEmail(), patched.getEmail());
        assertEquals(ENTITY.getPassword(), patched.getPassword());
    }

    @Test
    void whenDeleteTutor_thenReturnTrue() {
        UUID id = ENTITY.getId();
        mockFindById();

        boolean deleted = service.deleteTutor(id);
        assertTrue(deleted);
    }

    private void mockFindById() {
        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
    }
}