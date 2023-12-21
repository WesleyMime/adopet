package br.com.adopet.domain.repository;

import br.com.adopet.domain.model.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TutorRepository extends JpaRepository<TutorEntity, UUID> {

    Optional<TutorEntity> findByEmail(String email);
}
