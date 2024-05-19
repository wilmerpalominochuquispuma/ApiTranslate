package pe.edu.vallegrande.apitraslate.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.apitraslate.model.Translation;

@Repository
public interface TranslationRepository extends ReactiveCrudRepository<Translation, Long> {
}
