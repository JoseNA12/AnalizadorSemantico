/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;

/**
 *
 * @author Cristhian
 */
public class TablaVariables {

    static ArrayList<Variable> tablaV = new ArrayList();
    Variable elemento = null;
    public static boolean creado = false;

    public TablaVariables()
    {
        if (creado == false) {
            tablaV = new ArrayList();
            creado = true;
        }
    }

    public void insertar(String identificador, String tipo, Ambito ambito)
    {
        elemento = new Variable(identificador, tipo, ambito);
        tablaV.add(elemento);

    }

    public String buscar(String identificador)
    {
        for (int i = 0; i < tablaV.size(); i++)
        {
            if (tablaV.get(i).identificador.equals(identificador.trim()))
            {
                String aux = tablaV.get(i).tipo;
                return aux;
            }

        }
        return "-1";
    }

    class Variable {
        String identificador;
        String tipo;
        Ambito ambito;

        public Variable(String identificador, String tipo, Ambito ambito) {
            this.identificador = identificador;
            this.tipo = tipo;
            this.ambito = ambito;
        }


    }
}
