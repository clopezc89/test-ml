package cl.mutant.config;

import cl.mutant.model.Peticion;

public class Datos {

    public static final String[] PETICION_ES_MUTANTE = {"ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AAAAGG",
            "CCCCTA",
            "CCCGTC"};

    public static final String[] PETICION_NO_MUTANTE = {"ATGCGA",
            "CAGTGC",
            "TTATGT",
            "CATGGG"};

    public static final String[] PETICION_CARACTERES_INVALIDOS = {"ATGCGA",
            "CAGTGC",
            "TTATGT",
            "CATGGF"};

    public static final String CODIGO_ERROR = "1";
    public static final String CODIGO_ERROR_MSG = "Hay caracteres que no corresponden a una secuencia genetica";
    public static final Long CANTIDAD_MUTANTES = 2L;
    public static final Long CANTIDAD_HUMANOS = 2L;


    public static Peticion PETICION_ES_MUTANTE() {
        return Peticion.builder().dna(PETICION_ES_MUTANTE)
                .build();
    }

    public static Peticion PETICION_NO_MUTANTE() {
        return Peticion.builder().dna(PETICION_NO_MUTANTE)
                .build();

    }

    public static Peticion PETICION_CARACTERES_INVALIDOS() {
        return Peticion.builder().dna(PETICION_CARACTERES_INVALIDOS)
                .build();

    }
}
