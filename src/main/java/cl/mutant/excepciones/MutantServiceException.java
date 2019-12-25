
package cl.mutant.excepciones;

import lombok.*;

@Getter
@AllArgsConstructor
public class MutantServiceException extends RuntimeException {

    private String codigo;
    private String mensaje;

}
