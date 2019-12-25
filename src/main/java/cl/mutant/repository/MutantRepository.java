package cl.mutant.repository;

import cl.mutant.entity.MutantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutantRepository extends JpaRepository<MutantEntity, Long> {

    Long countByIsMutantTrue();
    Long countByIsMutantFalse();

}
