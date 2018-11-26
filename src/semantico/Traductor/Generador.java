package semantico.Traductor;

import sintactico.sym;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// https://codemyn.blogspot.com/2017/09/operaciones-aritmeticas-en-assembler.html

public class Generador {

	public static final int GOTO = 6070;
	public static final int LABEL = 14831;

	public static int contadorTemp = 0;
	public static int contadorEtiq = 0;
	protected static PrintStream out = System.out;


    public static String ExpresionesAritmeticas(PilaSemantica pPila) // unicamente binarias (y := f + 2)
    {
        String traduccion = ""; // add, sub, mul, div
        String var1, op, var2, var3;

        var1 = "mov al, " + pPila.pop_end().getValor();
        op = pPila.pop_end().getValor();
        var2 = pPila.pop_end().getValor();
        pPila.pop_end();
        var3 = pPila.pop_end().getValor();

        switch (op.toUpperCase())
        {
            case "+":
                traduccion += var1 + "\n";
                traduccion += "add al, " + var2 + "\n";
                traduccion += "mov " + var3 + ", al" + "\n";
                break;

            case "-":
                traduccion += var1 + "\n";
                traduccion += "sub al," + var2 + "\n";
                traduccion += "mov " + var3 + ", al" + "\n";
                break;

            case "*":
                traduccion += var1 + "\n";
                traduccion += "mul " + var2 + "\n";
                traduccion += "mov " + var3 + ", al" + "\n";
                break;

            case "/":
                traduccion += var1 + "\n";
                traduccion += "div " + var2 + "\n";
                traduccion += "mov " + var3 + ", al" + "\n";
                break;

            case "DIV":
                traduccion += var1 + "\n";
                traduccion += "div " + var2 + "\n";
                traduccion += "mov " + var3 + ", al" + "\n";
                break;

            case "MOD":
                traduccion += var1 + "\n";
                traduccion += "div " + var2 + "\n";
                traduccion += "mov " + var3 + ", ah" + "\n";
                break;
        }

        return traduccion;
    }

    public static String DeclaracionesVariables(PilaSemantica pPila)
    {
        // Se recibe de la forma:
        // <tipo> <:> <var_1> ... <var_n> <;>

        String traduccion = "";
        List<String> variables = new ArrayList<>();

        while (!pPila.isEmpty())
        {
            pPila.pop_end(); // sacar ';'

            String temp = pPila.pop_end().getValor();

            while (!temp.equals(":"))
            {
                variables.add(temp);
                temp = pPila.pop_end().getValor();
            }

            String tipo = pPila.pop_end().getValor();

            switch (tipo.toUpperCase())
            {
                case "INT":
                    for (int i = 0; i < variables.size(); i++)
                    {
                        traduccion += variables.get(i) + " dw " + "?" + "\n";
                    }
                    break;

                case "LONGINT":
                    for (int i = 0; i < variables.size(); i++)
                    {
                        traduccion += variables.get(i) + " dq " + "?" + "\n";
                    }
                    break;

                case "REAL":
                    for (int i = 0; i < variables.size(); i++)
                    {
                        traduccion += variables.get(i) + " dd " + "?" + "\n";
                    }
                    break;

                case "BOOLEAN":
                    for (int i = 0; i < variables.size(); i++)
                    {
                        traduccion += variables.get(i) + " db " + "0" + "\n";
                    }
                    break;

                default:
                    for (int i = 0; i < variables.size(); i++)
                    {
                        traduccion += variables.get(i) + " db " + "?" + "\n";
                    }
                    break;
            }
        }

        return traduccion;
    }

	public static void gc(int operacion, String arg1, String arg2, String resultado) {

        switch (operacion) {
            case sym.OPERADOR_ADICION:
                out.println("   " + resultado + " = " + arg1 + " + " + arg2 + ";");
                break;

            case sym.OPERADOR_SUSTRACCION:
                out.println("   " + resultado + " = " + arg1 + " - " + arg2 + ";");
                break;

            case sym.OPERADOR_MULTIPLICACION:
                out.println("   " + resultado + " = " + arg1 + " * " + arg2 + ";");
                break;

            case sym.OPERADOR_DIVISION:
                out.println("   " + resultado + " = " + arg1 + " / " + arg2 + ";");
                break;

            case sym.OPERADOR_ASIGNACION_1:
                out.println("   " + resultado + " = " + arg1 + ";");
                break;

            case sym.IF:
                out.println("   if (" + arg1 + " == " + arg2 + ") goto " + resultado + ";");
                break;

            case GOTO:
                out.println("   goto " + resultado + ";");
                break;

            case LABEL:
                out.println(resultado + ":");
                break;

            default:
                System.err.println("Error en la generación de código");
        }

    }

	public static String nuevaTemp()
	{
		return "t" + contadorTemp++;
	}

	public static String nuevaEtiq()
	{
		return "L" + contadorEtiq++;
	}
}
