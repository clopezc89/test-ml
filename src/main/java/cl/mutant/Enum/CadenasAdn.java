package cl.mutant.Enum;

public enum CadenasAdn {

    A("AAAA"),
    T("TTTT"),
    G("GGGG"),
    C("CCCC");

    private String cadena;


    CadenasAdn(String cadena) {
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }
}
