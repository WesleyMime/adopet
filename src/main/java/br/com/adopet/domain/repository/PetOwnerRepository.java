package br.com.adopet.domain.repository;

import br.com.adopet.domain.model.PetOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwnerEntity, UUID> {

    Optional<PetOwnerEntity> findByEmail(String email);
}
