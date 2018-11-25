package semantico.Traductor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PilaSemantica {

    private Stack<RegistroSemantico> pilaSemantica;
    //private List<RegistroSemantico> pilaSemantica;
    //private int tope = -1;


    public PilaSemantica()
    {
        pilaSemantica = new Stack<>();
        //pilaSemantica = new ArrayList<>();
    }

    public void push(int operacion, String valor)
    {
        pilaSemantica.push(new RegistroSemantico(operacion, valor));
        //pilaSemantica.add(0, new RegistroSemantico(operacion, valor));
        //tope++;
    }

    public void push(String valor)
    {
        pilaSemantica.push(new RegistroSemantico(-1, valor));
        //pilaSemantica.add(0, new RegistroSemantico(-1, valor));
        //tope++;
    }

    public void push(int operacion)
    {
        pilaSemantica.push(new RegistroSemantico(operacion, null));
        //pilaSemantica.add(0, new RegistroSemantico(operacion, null));
        //tope++;
    }

    public RegistroSemantico pop()
    {
        return pilaSemantica.pop();
        /*RegistroSemantico temp = peek();
        pilaSemantica.remove(tope);
        tope--;
        return temp;*/
    }

    public RegistroSemantico peek()
    {
        return pilaSemantica.peek();
        //return pilaSemantica.get(tope);
    }

    public Boolean isEmpty()
    {
        return pilaSemantica.isEmpty();
    }

    public void empty()
    {
        pilaSemantica.empty();
        //pilaSemantica.clear();
    }
}
