package cl.mutant.servicio.impl;

import cl.mutant.config.Datos;
import cl.mutant.excepciones.MutantServiceException;
import cl.mutant.model.Stats;
import cl.mutant.repository.MutantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MutantServiceImplTest {

    @InjectMocks
    private MutantServiceImpl servicio;

    @Mock
    private MutantRepository mutantRepository;


    @Test
    public void isMutantTest() {

        ResponseEntity responseEntity = servicio.verification(Datos.PETICION_ES_MUTANTE());

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        200
                )
        );

    }

    @Test
    public void isNotMutantTest() {

        ResponseEntity responseEntity = servicio.verification(Datos.PETICION_NO_MUTANTE());

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        403
                )
        );

    }

    @Test(expected = MutantServiceException.class)
    public void caracteresInvalidosTest() {

        ResponseEntity responseEntity = servicio.verification(Datos.PETICION_CARACTERES_INVALIDOS());

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        412
                )
        );

    }

    @Test
    public void statsTest() {

        when(mutantRepository.countByIsMutantTrue()).thenReturn(Datos.CANTIDAD_MUTANTES);
        when(mutantRepository.countByIsMutantFalse()).thenReturn(Datos.CANTIDAD_HUMANOS);
        ResponseEntity responseEntity = servicio.getStats();

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        200
                )
        );

        Stats stats = (Stats) responseEntity.getBody();

        assertThat(stats.getCount_human_dna(),
                is(
                        2L
                )
        );

        assertThat(stats.getCount_mutant_dna(),
                is(
                        2L
                )
        );

        assertThat(stats.getRatio(),
                is(
                        1L
                )
        );

    }




}
