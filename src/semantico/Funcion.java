package semantico;

import java.util.ArrayList;

public class Funcion extends Simbolo {

    private ArrayList<Variable> parametros;
    private TipoDato tipoRetorno;

    public Funcion(){
        super();
        parametros = new ArrayList<>();
        tipoRetorno = null;
    }

    public Funcion(String identificador, String ambito, ArrayList<Variable> parametros, TipoDato tipoRetorno,
                   int fila, int columna){
        super(identificador, ambito, fila, columna);

        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
    }


    //GETTERS Y SETTERS

    public ArrayList<Variable> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Variable> parametros) {
        this.parametros = parametros;
    }

    public TipoDato getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(TipoDato tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }
}
