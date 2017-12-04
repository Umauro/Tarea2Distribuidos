/*
 * Tarea 2 Sistemas Distribuidos 2017-2
 *
 * Primera versión de la tarea para la implementación de semaforos distribuidos,
 * utilizando el algoritmo Suzuki-Kasami, en conjunto con Java RMI.
 * Desarollada por Andrés Huerta (@MeatBoyUSM) y Felipe Vega (@Umauro)
 *
 * 28/11/2017
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;


/*
 * Token utilizado por el algoritmo Suzuki-Kasami.
 *
 * v0.0.1
 * 28 nov 2017
 * Andrés Huerta
 * Felipe Vega
 */

public class Token implements Serializable{
    /* Cola de procesos en espera del Token */
    Vector<Integer> listaProcesos;
    Queue<Integer> colaRequest;
    int proxId;

    /* Constructor de la clase Token */
    public Token(int n){
        colaRequest = new LinkedList();
        listaProcesos = new Vector(n);
        for(int i=0; i<n; i++){
            listaProcesos.add(0);
        }
    }

    public void encolarProceso(int id){
        colaRequest.add(id);
    }

    public int getProxId(){
        return colaRequest.poll();
    }
}
