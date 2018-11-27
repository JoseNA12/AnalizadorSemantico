package semantico.Traductor;

import sintactico.sym;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// https://codemyn.blogspot.com/2017/09/operaciones-aritmeticas-en-assembler.html

public class Generador {

	public static final int GOTO = 6070;
	public static final int LABEL = 14831;

	public static int contadorTemp = 0;
	public static int contadorEtiq = 1;
	public static String etiqAnterior = "L0";
    public static String etiqSiguiente = "";
	protected static PrintStream out = System.out;

	public static String startLabel = "";
	public static String exitLabel = "";

	private static Deque<String> pilaEnd = new LinkedList<>();


    public static String ExpresionesAritmeticas(PilaSemantica pPila) // unicamente binarias [y := f + 2]
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

    public static String ExpresionesAritmeticas_(PilaSemantica pPila) // a++ | false -> ++a
    {
        String traduccion = "";

        String op = pPila.pop_end().getValor();
        String var = pPila.pop_end().getValor();
        String tempOp = "";

        switch (op)
        {
            case "++":
                tempOp = "inc ";
                break;

            case "--":
                tempOp = "dec ";
                break;
        }
        traduccion += tempOp + var;

        return traduccion;
    }

    public static String ExpresionesAritmeticas__(PilaSemantica pPila) // a := b++ | a := --b
    {
        String traduccion = "";
        // <id> <id> <++>

        String op = pPila.pop_end().getValor();
        String id2 = pPila.pop_end().getValor();
        String id = pPila.pop_end().getValor();
        String tempOp = "";

        switch (op)
        {
            case "++":
                tempOp = "inc ";
                break;

            case "--":
                tempOp = "dec ";
                break;
        }
        traduccion += tempOp + id2 + "\n";
        traduccion += "mov " + id + ", " + id2 + "\n";

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

    public static String EstrucControl_IF(PilaSemantica pPila) // unicamente de la forma [if (a < b)]
    {
        String operando1 = pPila.pop_init().getValor();
        String operador = pPila.pop_init().getValor();
        String operando2 = pPila.pop_init().getValor();

        String traduccion =
                "cmp (" + operando1 + " " + operador + " " + operando2 + ")\n" +
                getSalto(operador) + " ";

        etiqSiguiente = nuevaEtiq();
        traduccion += etiqSiguiente + "\n";  //else o end
        pilaEnd.add(etiqSiguiente);

        return traduccion;
    }

    public static String EstrucControl_IF_ELSE()
    {
        etiqAnterior = pilaEnd.pollLast();
        etiqSiguiente = nuevaEtiq();

        String traduccion = "jmp " + etiqSiguiente + "\n";
        traduccion += etiqAnterior + ":";
        pilaEnd.add(etiqSiguiente);

        return traduccion;
    }

    public static String EstrucControl_END()
    {
        return pilaEnd.pollLast() + ":\n";
    }

    public static String EstrucControl_WHILE(PilaSemantica pPila)
    {
        etiqAnterior = nuevaEtiq();

        pilaEnd.add(etiqAnterior);

        String traduccion = "startWhile_" + etiqAnterior + ":\n";

        String operando1 = pPila.pop_init().getValor();
        String operador = pPila.pop_init().getValor();
        String operando2 = pPila.pop_init().getValor();

        traduccion += "cmp (" + operando1 + " " + operador + " " + operando2 + ")\n";
        traduccion += getSalto(operador) + " exitWhile_" + etiqAnterior + "\n";
        // contenido del while

        return traduccion;
    }

    public static String UltimosLabelsWhile()
    {
        String sopaDeMacaco = pilaEnd.pollLast();
        String upa = "jmp " + "startWhile_" + sopaDeMacaco + " \n" + "exitWhile_" + sopaDeMacaco + ":\n";
        return upa;
    }

    private static String getSalto(String operador){
        switch(operador){
            case "<":   return "jge";
            case "<=":  return "jg";
            case ">":   return "jle";
            case ">=":  return "jl";
            case "<>":  return "je";
            case "=":   return "jne";
            default:    return "jz";
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
