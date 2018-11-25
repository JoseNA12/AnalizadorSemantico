package semantico.Traductor;

import sintactico.sym;

import java.io.PrintStream;

public class Generador {

	public static final int GOTO = 6070;
	public static final int LABEL = 14831;

	public static int contadorTemp = 0;
	public static int contadorEtiq = 0;
	protected static PrintStream out = System.out;


	public static void dele(DTO p)
    {
        gc(p.getOperador(), p.getArg1(), p.getArg2(), p.getResultado());
    }

	public static void gc(int operacion, String arg1, String arg2, String resultado)
    {
		switch(operacion)
        {
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
