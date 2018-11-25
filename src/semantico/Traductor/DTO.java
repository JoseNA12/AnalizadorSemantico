package semantico.Traductor;

public class DTO {

    private int operador = -1;
    private String arg1 = null;
    private String arg2 = null;
    private String resultado = null;

    public DTO() {
    }

    public void Start()
    {
        Generador.dele(this);
        this.Reset();
    }

    public void Reset()
    {
        operador = -1;
        arg1 = null;
        arg2 = null;
        resultado = null;
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operadorr) {
        operador = operadorr;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg(String a)
    {
        if (arg1 == null)
        {
            arg1 = a;
        }
        else
        {
            arg2 = a;
        }

    }

    public String getArg2() {
        return arg2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultadoo) {
        resultado = resultadoo;
    }

}
