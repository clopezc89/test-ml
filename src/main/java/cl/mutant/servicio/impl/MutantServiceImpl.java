package cl.mutant.servicio.impl;

import cl.mutant.Enum.CadenasAdn;
import cl.mutant.entity.MutantEntity;
import cl.mutant.excepciones.MutantServiceException;
import cl.mutant.model.Peticion;
import cl.mutant.model.Stats;
import cl.mutant.model.Verification;
import cl.mutant.repository.MutantRepository;
import cl.mutant.servicio.IMutantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class MutantServiceImpl implements IMutantService {

    @Value("${codigo.error}")
    private String errorCaracteresInvalidos;

    @Value("${codigo.error.msg}")
    private String errorCaracteresInvalidosMsg;

    @Autowired
    private MutantRepository mutantRepository;

    private List<Character> caracteresValidos = Arrays.asList('A', 'C', 'T', 'G');

    public ResponseEntity verification(Peticion peticion) {

        caracteresValidos(peticion.getDna());

        if (isMutant(peticion)) {
            saveDna(peticion, true);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            saveDna(peticion, false);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }

    public ResponseEntity<Stats> getStats() {

        Long isMutant = mutantRepository.countByIsMutantTrue();
        Long isHuman = mutantRepository.countByIsMutantFalse();
        Stats stats = Stats.builder()
                .count_mutant_dna(isMutant)
                .count_human_dna(isHuman)
                .ratio(isMutant/isHuman)
                .build();

        return new ResponseEntity<>(stats, HttpStatus.OK);

    }

    private void saveDna(Peticion peticion, Boolean isMutant) {
        MutantEntity mutantEntity = MutantEntity.builder()
                .dna(Arrays.toString(peticion.getDna()))
                .isMutant(isMutant)
                .build();

        try {
            mutantRepository.save(mutantEntity);
        } catch (DataIntegrityViolationException e) {
            log.info("DNA YA EXISTE", e);
        }

    }

    private boolean isMutant(Peticion peticion) {

        Verification verification = Verification.builder().veces(0)
                .build();

        busquedaHorizontal(peticion.getDna(), verification);
        busquedaVertical(peticion.getDna(), verification);
        busquedaOblicua(peticion.getDna(), verification);

        return verification.getVeces() >= 1;
    }

    private void busquedaOblicua(String[] dna, Verification verificacion) {

        List<String> dnas = Arrays.asList(dna);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new LinkedList<>(Arrays.asList(dna));

        if (dnas.size() >= 4) {
            oblicuaArriba(dnas, stringBuilder, 1);
            oblicuaArriba(dnas, stringBuilder, 2);
            oblicuaAbajo(list, stringBuilder);
            getVerificacion(verificacion, stringBuilder);
        }


    }

    private void oblicuaAbajo(List<String> dnas, StringBuilder stringBuilder) {

        while (dnas.size() >= 4) {

            stringBuilder.append("|");

            for (int j = 0; dnas.size() > j; j++) {
                stringBuilder.append(dnas.get(j).charAt(j));
            }

            dnas.remove(0);

        }

    }

    private void oblicuaArriba(List<String> dnas, StringBuilder stringBuilder, int j) {

        stringBuilder.append("|");

        for (String s : dnas) {
            if (j > 5) {
                break;
            }
            stringBuilder.append(s.charAt(j));
            j++;
        }
    }

    private void busquedaVertical(String[] dna, Verification verificacion) {

        List<String> dnas = Arrays.asList(dna);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; dnas.size() > i; i++) {

            stringBuilder.append("|");

            for (String s : dnas) {
                stringBuilder.append(s.charAt(i));
            }

        }

        getVerificacion(verificacion, stringBuilder);
    }

    private void busquedaHorizontal(String[] dna, Verification verificacion) {

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : dna) {
            stringBuilder.append(s).append("|");
        }

        getVerificacion(verificacion, stringBuilder);
    }

    private void getVerificacion(Verification verificacion, StringBuilder stringBuilder) {

        verificaCadena(stringBuilder.toString(), CadenasAdn.A.getCadena(), verificacion);
        verificaCadena(stringBuilder.toString(), CadenasAdn.C.getCadena(), verificacion);
        verificaCadena(stringBuilder.toString(), CadenasAdn.T.getCadena(), verificacion);
        verificaCadena(stringBuilder.toString(), CadenasAdn.G.getCadena(), verificacion);

    }

    private void verificaCadena(String str, String tipoCadena, Verification verificacion) {

        Integer veces = verificacion.getVeces();

        while (str.contains(tipoCadena)) {
            str = str.replaceFirst(tipoCadena, "");
            verificacion.setVeces(++veces);
        }

    }

    private void caracteresValidos(String[] dna) {
        for (String s : dna) {
            for (int k = 0; k < s.length(); k++) {
                if (!caracteresValidos.contains(s.charAt(k))) {
                    throw new MutantServiceException(errorCaracteresInvalidos, errorCaracteresInvalidosMsg);
                }
            }
        }
    }

}


