package cl.mutant.excepciones;


import cl.mutant.config.Datos;
import cl.mutant.servicio.impl.MutantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviceTest {

    @InjectMocks
    ControllerAdvice controllerAdvice;

    @Mock
    Exception exception;

    @Test
    public void controllerAdviceTest() {

        Exception ex = new MutantServiceException(Datos.CODIGO_ERROR, Datos.CODIGO_ERROR_MSG);

        ResponseEntity responseEntity = controllerAdvice.errorMutantService(ex);

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        412
                )
        );
    }

    @Test
    public void throwableTest() {

        ResponseEntity responseEntity = controllerAdvice.procesarErrorNoControlado(exception);

        assertThat(responseEntity.getStatusCodeValue(),
                is(
                        400
                )
        );
    }






}
