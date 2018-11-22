package semantico;

// https://www.tutorialspoint.com/es/compiler_design/compiler_design_symbol_table.htm

public class Simbolo {

    private String nombre;
    private String tipo;
    private String atributo;


    public Simbolo(String nombre, String tipo, String atributo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.atributo = atributo;

        /*
        static int interest;
        new Simbolo(interest, int, static);
         */
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAtributo() {
        return atributo;
    }
}
