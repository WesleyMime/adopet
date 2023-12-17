package br.com.adopet.domain.controller;

import br.com.adopet.domain.model.PetOwnerEntity;
import br.com.adopet.domain.model.PetOwnerForm;
import br.com.adopet.domain.service.PetOwnerService;
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
public class PetOwnerController {

    private PetOwnerService PetOwnerService;

    @GetMapping
    @Transactional
    public ResponseEntity<List<PetOwnerEntity>> getAllPetOwners() {
        List<PetOwnerEntity> all = PetOwnerService.getAllPetOwners();

        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<PetOwnerEntity> getPetOwnerById(@PathVariable("id") UUID id) {
        PetOwnerEntity PetOwnerEntity = PetOwnerService.getPetOwnerById(id);
        return ResponseEntity.ok(PetOwnerEntity);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PetOwnerEntity> postPetOwner(@RequestBody PetOwnerForm PetOwnerForm) {
        PetOwnerEntity PetOwnerEntity = PetOwnerService.postPetOwner(PetOwnerForm);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/tutores/{id}")
                .buildAndExpand(PetOwnerEntity.getId())
                .toUri();
        return ResponseEntity.created(uri).body(PetOwnerEntity);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PetOwnerEntity> updatePetOwner(@PathVariable("id") UUID id, @RequestBody PetOwnerForm PetOwnerForm) {
        return ResponseEntity.ok(PetOwnerService.updatePetOwner(id, PetOwnerForm));
    }


    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<PetOwnerEntity> patchPetOwner(@PathVariable("id") UUID id, @RequestBody PetOwnerForm PetOwnerForm) {
        PetOwnerEntity PetOwnerEntity = PetOwnerService.patchPetOwner(id, PetOwnerForm);
        return ResponseEntity.ok(PetOwnerEntity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<PetOwnerEntity> deletePetOwner(@PathVariable("id") UUID id) {
        PetOwnerService.deletePetOwner(id);
        return ResponseEntity.noContent().build();
    }
}
