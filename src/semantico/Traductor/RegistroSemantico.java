package semantico.Traductor;

import sintactico.sym;

public class RegistroSemantico {

    private int operacion;
    private String valor;


    public RegistroSemantico(String valor) {
        this.operacion = -1;
        this.valor = valor;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString()
    {
        String tuEstrin = "";
        if (operacion == -1)
        {
            return "{valor -> " + valor + "}";
        }
        else if(valor == null)
        {
            return "{operacion -> " + sym.terminalNames[operacion] + " (" + operacion + ")}";
        }
        else
        {
            return "{operacion -> " + sym.terminalNames[operacion] + " (" + operacion + ")" +
                    ", valor = '" + valor + '\'' +
                    '}';
        }
    }
}
