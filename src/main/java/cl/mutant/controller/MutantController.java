package cl.mutant.controller;

import cl.mutant.model.Peticion;
import cl.mutant.model.Stats;
import cl.mutant.servicio.IMutantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
class MutantController {


    @Autowired
    IMutantService iMutantService;

    @PostMapping(
            path = "/mutant/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll(
            @RequestBody Peticion peticion) {

        log.info("dna {}", peticion);

        return iMutantService.verification(peticion);
    }


    @GetMapping(
            path = "/stats",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stats> getAll() {

        return iMutantService.getStats();
    }


}
