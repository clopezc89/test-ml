package cl.mutant.excepciones;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {


    public ControllerAdvice() {
        super();
    }

    @ExceptionHandler(value = MutantServiceException.class)
    public ResponseEntity errorMutantService(Exception ex) {

        MutantServiceException mse = (MutantServiceException) ex;

        escribirLog(mse.getCodigo(), mse.getMensaje());


        return new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
    }


    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity procesarErrorNoControlado(Exception ex) {

        escribirLog("Ocurrio un error: ", ex);

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private void escribirLog(String codigo, String mensaje) {

        log.error("Error {}", codigo + " " + mensaje);
    }

    private void escribirLog(String mensaje, Exception ex) {

        log.error(mensaje, ex);
    }


}