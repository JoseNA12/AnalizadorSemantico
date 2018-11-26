/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import org.reactfx.value.Var;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author Cristhian
 */
public class TablaSimbolos {

    static ArrayList<Simbolo> tablaSimbolos = new ArrayList();

    //pila que almacena todos los tipos de datos que se definen (algunos deben ser desechados cuando hay error)
    private Deque<TipoDato> tiposDato = new LinkedList();

    //contiene todas las variables EN ORDEN SECUENCIAL
    public ArrayList<Variable> variables;
    //una pila que contiene una pila de variables
    private Deque<Deque<Variable>> pilaVariablesSinTipo;
    //pila de variables que inician sin tipo (por precedencia en las gramáticas)
    private Deque<Variable> variablesSinTipo;

    //Almacena los parámetros de una funcion/procedimiento
    public ArrayList<Variable> parametros;
    private boolean parametrosCorrectos;

    //Guarda las variables que están siendo usadas dentro de cuerpo para luego ser verificadas
    public ArrayList<Variable> variablesUsadas;

    //Esta guarda los parámetros que llaman a una función/procedimiento
    private ArrayList<Object> parametrosLlamada;

    public TablaSimbolos()
    {
        tablaSimbolos = new ArrayList();

        variables = new ArrayList<>();
        pilaVariablesSinTipo = new LinkedList<>();
        variablesSinTipo = new LinkedList<>();

        parametros = new ArrayList<>();
        parametrosCorrectos = true;

        variablesUsadas = new ArrayList<>();

        parametrosLlamada = new ArrayList<>();
    }

    /**
     * Inserta un símbolo a la tabla de símbolos xD
     * @param simbolo Simbolo a insertar (Variable o Función)
     */
    public void insertar(Simbolo simbolo)
    {
        tablaSimbolos.add(simbolo);
    }

    /**
     * Revisa si ya existe un símbolo en la tabla de símbolos.
     * @param simbolo Variable o Función
     * @return True existe, False no existe
     */
    public boolean existeSimbolo(Simbolo simbolo){
        Simbolo auxSimbolo;

        if(simbolo instanceof Variable){
            Variable aux = (Variable) simbolo;

            for(int i=0; i<tablaSimbolos.size(); i++){
                auxSimbolo = tablaSimbolos.get(i);
                if(auxSimbolo instanceof Variable){
                    if(auxSimbolo.identificador.equals(aux.identificador) &&
                        auxSimbolo.ambito.equals(aux.ambito))
                        return true;
                }
            }
            return false;
        }
        else if(simbolo instanceof Funcion){
            Funcion funcion = (Funcion) simbolo;
            Funcion aux;
            int cantidadIguales;

            for(int i=0; i<tablaSimbolos.size(); i++){
                auxSimbolo = tablaSimbolos.get(i);
                if(auxSimbolo instanceof Funcion){
                    aux = (Funcion)auxSimbolo;
                    cantidadIguales = 0;

                    if(aux.identificador.equals(funcion.identificador) &&
                            aux.getParametros().size() == funcion.getParametros().size()){

                        for(int j=0; j<funcion.getParametros().size(); j++){
                            Variable varAux = aux.getParametros().get(j);
                            Variable varFuncion = funcion.getParametros().get(j);

                            //si en algún momento difieren los parámetros
                            if(varAux.getTipo() != varFuncion.getTipo()){
                                break;
                            }
                            cantidadIguales++;
                        }
                        //si llega aquí significa que sí tienen los parámetros iguales
                        if(cantidadIguales == funcion.getParametros().size())
                            return true;
                    }
                }
            }

            return false;
        }
        return false; //en realidad nunca va a llegar aquí pero yolo
    }

    /**
     * Verifica si un identificador existe dentro de las variables en la tabla de símbolos
     * @param identificador Nombre de la variable
     * @param ambito Nombre de la función o procedimiento al que pertenece la variable, null si es del cuerpo principal
     * @return True existe, False no existe
     */
    public boolean existeVariable(String identificador, String ambito){
        for(Simbolo s : tablaSimbolos){
            if(s instanceof Variable){
                if(s.identificador.equals(identificador) &&
                        (s.ambito.equals("Global") || s.ambito.equals(ambito))){
                    return true;
                }
            }
        }
        return false;
    }

    //############################### QUICKSORT sobre las variables porque me lleva puta >:v

    /* Esta funcion toma el ultimo elemento como pivot, lo coloca es la posición correcta del arreglo
       y coloca los más pequeños que el pivot a la izquierda del pivot, los más grandes a la derecha */
    private int partition(ArrayList<Variable> vars, int low, int high)
    {
        Variable pivot = vars.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++){
            // Si la fila es menor
            if (vars.get(j).getFila() < pivot.getFila()) {
                i++;
                // swap arr[i] and arr[j]
                Variable temp = vars.get(i);
                vars.set(i, vars.get(j));
                vars.set(j, temp);
            }
            //Si la fila es igual pero la columna es menor
            else if(vars.get(j).getFila() == pivot.getFila() &&
                    vars.get(j).getColumna() <= pivot.getColumna()){
                i++;
                // swap arr[i] and arr[j]
                Variable temp = vars.get(i);
                vars.set(i, vars.get(j));
                vars.set(j, temp);
            }
        }
        // cambio(swap) arr[i+1] y arr[high] (o el pivot)
        Variable temp = vars.get(i+1);
        vars.set(i+1, vars.get(high));
        vars.set(high, temp);

        return i+1;
    }
    /**
     * Aplica un quicksort sobre las variables, porque el orden cambia mucho dependiendo de la cantidad
     * @param vars el atributo variables de esta clase
     * @param low indice de inicio
     * @param high indice final
     */
    private void quicksort(ArrayList<Variable> vars, int low, int high)
    {
        if (low < high)
        {
            //pi = partitioning index
            int pi = partition(vars, low, high);

            // Recursivamnte ordena los elementos antes y después de la particion
            quicksort(vars, low, pi-1);
            quicksort(vars, pi+1, high);
        }
    }

    //Llama al quicksort porque las variables no tienen orden >:v
    public void ordenarVariables(){
        quicksort(variables, 0, variables.size()-1);
    }


    //################################ VARIABLES

    /**
     * Agrega la primera variable del no terminal _variables
     *  (la de menor precedencia, por lo tanto la última en agregarse)
     * @param identificador Nombre de la variable
     */
    public void agregarVariable(String identificador, int fila, int columna){
        Variable var = new Variable(identificador, tiposDato.pollLast(), "", fila+1, columna);

        //Agrega la primera variable de la producción
        variables.add(var);

        //Saca las últimas variables sin tipo
        if(pilaVariablesSinTipo.size() != 0){
            Deque<Variable> auxVarSinTipo = pilaVariablesSinTipo.pollLast();
            while(auxVarSinTipo.size() != 0){
                variables.add(auxVarSinTipo.pollFirst());
            }
        }


        /*System.out.println();
        for(Variable v: variables) {
            System.out.println(v.identificador + " " + ((Variable) v).getTipo() + " " + v.fila + "," + v.columna);
        }*/
    }

    /**
     * Agrega una variable iniciada sin tipo, proviene del no terminal __variables
     * @param identificador Nombre de la variable
     */
    public void agregarVariableSinTipo(String identificador, int fila, int columna){
        Variable var = new Variable();
        var.identificador = identificador;
        var.setFila(fila+1);
        var.setColumna(columna);

        variablesSinTipo.add(var);
    }

    /**
     * Retorna una variable existente dentro de la tabla de símbolos
     * @param nombreVariable Identificador de la variable
     * @param ambito Ámbito en el que se quiere la variable (si no se encuentra se busca en Global)
     * @return Variable si la encuentra, null si no la encuentra
     */
    private Variable getVariable(String nombreVariable, String ambito){
        //Le da prioridad a las variables del ámbito
        for(Simbolo s : tablaSimbolos){
            if(s instanceof Variable &&
                    s.identificador.equals(nombreVariable) && s.ambito.equals(ambito)){
                return (Variable) s;
            }
        }

        //Si no la encuentra en el ámbito, busca en las globales
        for(Simbolo s : tablaSimbolos){
            if(s instanceof Variable &&
                    s.identificador.equals(nombreVariable) && s.ambito.equals("Global")){
                return (Variable) s;
            }
        }

        return null;
    }


    //################################ FUNCIONES

    /**
     * Crea una Función, le agrega parámetros y tipo de dato
     * @param identificador Identificador de la función
     * @param fila Número de fila donde fue declarada
     * @param columna Número de columna donde fue declarada
     * @return Función con parámetros y tipo
     */
    public Funcion crearFuncion(String identificador, int fila, int columna){
        Funcion funcion = new Funcion(identificador, "", parametros, tiposDato.pollLast(), fila+1, columna);
        return funcion;
    }

    /**
     * Agrega un parámetro a la lista de parámetros que se agregan a una función
     * @param identificador Identificador del parámetro
     * @param fila Número de fila donde fue declarado
     * @param columna Número de columna donde fue declarado
     */
    public void agregarParametro(String identificador, int fila, int columna){
        Variable parametro = new Variable();
        parametro.setIdentificador(identificador);
        parametro.setTipo(tiposDato.pollLast());
        parametro.setFila(fila);
        parametro.setColumna(columna);

        parametros.add(0,parametro);
    }

    /**
     * Obtiene una lista con todos los parámetros erroneos que hayan el la declaración de la función
     * @return Lista de Varibles para parámetros
     */
    public ArrayList<Variable> getParametrosErroneos(){
        ArrayList<Variable> erroneos = new ArrayList<>();

        Variable param1, param2;
        for(int i=0; i<parametros.size(); i++){
            param1 = parametros.get(i);
            for(int j=i+1; j<parametros.size(); j++){
                param2 = parametros.get(j);

                if(param1.identificador.equals(param2.identificador) &&
                        !erroneos.contains(param2)
                ){
                    erroneos.add(param2);
                }
            }
        }

        parametrosCorrectos = (erroneos.size() == 0);

        if(!parametrosCorrectos){ //hay parámetros repetidos
            return erroneos;
        }
        else{ //no hay parámetros repetidos
            return null;
        }
    }

    /**
     * Es para las funciones que fallan, agregan un tipo y se queda ahí
     *  entonces hay que desecharlo
     */
    public void desecharUltimoTipoDato(){
        tiposDato.pollLast();
    }


    /**
     * Cuando se llama a una función en el código, se agrega dicho parámetro a
     *  la lista de parámetros de la llamada
     * @param param Puede ser un string (referencia a Variable), o Tipo de Dato
     */
    public void agregarParametroLlamada(Object param){
        parametrosLlamada.add(param);
    }

    /**
     * Valida si los parámetros en la llamada de la función son correctos con respecto
     *  a las funciones / procedimientos existentes
     * @param identificador Identificador de la función a llamar
     * @return True es correcto
     *         False no se encuentra una función con esas características en la tabla de símbolos
     */
    public boolean validarLlamadaFuncion(String identificador){
        Funcion funcionLlamada;
        //System.out.println(identificador + " " + parametros.size());

        for(int i=0; i<tablaSimbolos.size(); i++){
            if(tablaSimbolos.get(i) instanceof Funcion &&
                    tablaSimbolos.get(i).identificador.equals(identificador)){
                funcionLlamada = (Funcion)tablaSimbolos.get(i);

                if(parametrosLlamada.size() == funcionLlamada.getParametros().size()){
                    boolean iguales  = true;
                    for(int k=0; k<parametrosLlamada.size(); k++){
                        Object param = parametrosLlamada.get(k);
                        Variable paramFuncion = funcionLlamada.getParametros().get(k);

                        if(param instanceof String){    //Es una variable
                            Variable var = getVariable(param.toString(), identificador);
                            if((var == null) || (var.getTipo() != paramFuncion.getTipo())){
                                iguales = false;
                                break;
                            }
                        }
                        else if(param instanceof TipoDato){
                            if(param != paramFuncion.getTipo()){
                                iguales = false;
                                break;
                            }
                        }else{
                            break;
                        }
                    }
                    if(iguales){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Limpia la lista de parámetros que llaman a una función
     */
    public void limpiarParametrosLlamada(){
        parametrosLlamada = new ArrayList<>();
    }


    //################################ COMPARTIDO XD

    /**
     * Agrega el tipo de dato a la pila de Tipos de Datos
     * Y además inicializa la última lista de variables sin tipo
     * @param tipoDato Valor del enum Tipo de Dato
     */
    public void agregarTipoDato(TipoDato tipoDato){
        if(variablesSinTipo.size() != 0){
            for(Variable var : variablesSinTipo){
                var.setTipo(tipoDato);
            }

            pilaVariablesSinTipo.add(variablesSinTipo);
            variablesSinTipo = new LinkedList<>();
        }
        this.tiposDato.add(tipoDato);
    }

    /**
     * Es para las variables que se llaman en cuerpo, para comparar contra las que hay en la tabla
     * Se almacenan porque no se les puede meter ámbito al inicio.
     * @param identificador Nombre de la variable a llamar
     * @param fila Numero de fila desde donde se llama
     * @param columna Numero de columna desde donde se llama
     */
    public void agregarVariableUsada(String identificador, int fila, int columna){
        Variable var = new Variable(identificador, null, "", fila, columna);
        variablesUsadas.add(var);
    }

    public ArrayList<Simbolo> getTablaSimbolos(){
        return tablaSimbolos;
    }

    /**
     * Define un string con toda la información existente en la tabla de símbolos
     * @return String con la información de todos los símbolos
     */
    public String verTablaSimbolos(){
        String msj = "";
        for(Simbolo s : tablaSimbolos){
            if(s instanceof Variable){
                msj += "Variable: " + s.identificador + ", tipo: " + ((Variable) s).getTipo() + "\n";
            }
            else if(s instanceof Funcion){
                if(((Funcion) s).getTipoRetorno() == null)  //Es procedimiento
                    msj += "Procedimiento: " + s.identificador + "\n";
                else                                        //Es funcion
                    msj += "Función: " + s.identificador + ", tipo de retorno: " + ((Funcion) s).getTipoRetorno()  + "\n";

                ArrayList<Variable> params = ((Funcion) s).getParametros();
                msj += "\tCantidad de parámetros: " + params.size() + "\n";
                if(params.size() != 0){
                    msj += "\tParámetros: ";
                    for(Variable v : params){
                        msj += v.identificador + ": " + v.getTipo() + ", ";
                    }
                    msj = msj.substring(0, msj.length()-2) + ".\n";
                }
            }
        }

        return msj;
    }

}
