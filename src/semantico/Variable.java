package semantico;

public class Variable extends Simbolo {
    private TipoDato tipo;

    public Variable(){
        super();
        tipo = null;
    }

    public Variable(String identificador, TipoDato tipo, String ambito, int fila, int columna) {
        super(identificador, ambito, fila, columna);

        this.tipo = tipo;
    }


    //GETTERS Y SETTERS

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }
}
