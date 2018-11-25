package semantico;

public class Simbolo {

    protected String identificador;
    protected String ambito;

    protected int fila;
    protected int columna;

    public Simbolo(){
        identificador = "";
        ambito = "";
        fila = -1;
        columna = -1;
    }

    public Simbolo(String identificador, String ambito, int fila, int columna){
        this.identificador = identificador;
        this.ambito = ambito;
        this.fila = fila;
        this.columna = columna;
    }


    //GETTERS Y SETTERS

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public int getFila() { return fila; }

    public void setFila(int fila) { this.fila = fila; }

    public int getColumna() { return columna; }

    public void setColumna(int columna) { this.columna = columna; }
}
