package br.com.adopet.domain.controller;

import br.com.adopet.domain.model.TutorEntity;
import br.com.adopet.domain.model.TutorForm;
import br.com.adopet.domain.service.TutorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tutores")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TutorController {

    private TutorService TutorService;

    @GetMapping
    @Transactional
    public ResponseEntity<List<TutorEntity>> getAllTutores() {
        List<TutorEntity> all = TutorService.getAllTutores();

        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TutorEntity> getTutorById(@PathVariable("id") UUID id) {
        TutorEntity TutorEntity = TutorService.getTutorById(id);
        return ResponseEntity.ok(TutorEntity);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TutorEntity> postTutor(@RequestBody TutorForm TutorForm) {
        TutorEntity TutorEntity = TutorService.postTutor(TutorForm);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/tutores/{id}")
                .buildAndExpand(TutorEntity.getId())
                .toUri();
        return ResponseEntity.created(uri).body(TutorEntity);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TutorEntity> updateTutor(@PathVariable("id") UUID id, @RequestBody TutorForm TutorForm) {
        return ResponseEntity.ok(TutorService.updateTutor(id, TutorForm));
    }


    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<TutorEntity> patchTutor(@PathVariable("id") UUID id, @RequestBody TutorForm TutorForm) {
        TutorEntity TutorEntity = TutorService.patchTutor(id, TutorForm);
        return ResponseEntity.ok(TutorEntity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<TutorEntity> deleteTutor(@PathVariable("id") UUID id) {
        TutorService.deleteTutor(id);
        return ResponseEntity.noContent().build();
    }
}
