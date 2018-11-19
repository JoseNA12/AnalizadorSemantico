/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import java.util.Map;


public class LineaToken {
    public String token;
    public String tipoToken;
    public Map<Integer, Integer> lineasAparicion; //< numeroLinea, cantidadApariciones >

    public LineaToken() { /*por defecto*/ }

    /**
     * Contructor de Linea de Token
     * @param token Token analizado
     * @param tipoToken Tipo del Token Analizado
     * @param lineasAparicion Mapa con las apariciones del Token y su cantidad
     */
    public LineaToken(String token, String tipoToken, Map<Integer, Integer> lineasAparicion){
        this.token = token;
        this.tipoToken = tipoToken;
        this.lineasAparicion = lineasAparicion;
    }

    /**
     * Agrega una nueva línea de aparición al Token
     * Si el token no existe crea una nueva línea de aparicion iniciada en 1, sino aumenta el número de apariciones
     * @param numeroLinea Línea en la que se encontró el Token
     */
    public void agregarLinea(int numeroLinea){
        Integer numeroApariciones = lineasAparicion.get(numeroLinea);
        if(numeroApariciones == null){
            lineasAparicion.put(numeroLinea, 1);
        }else{
            lineasAparicion.put(numeroLinea, numeroApariciones + 1);
        }
    }
}
