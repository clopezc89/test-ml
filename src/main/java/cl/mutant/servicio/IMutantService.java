package cl.mutant.servicio;

import cl.mutant.model.Peticion;
import cl.mutant.model.Stats;
import org.springframework.http.ResponseEntity;

public interface IMutantService {

    ResponseEntity verification(Peticion dna);

    ResponseEntity<Stats> getStats();


}
