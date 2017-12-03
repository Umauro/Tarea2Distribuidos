import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.*;
import java.net.UnknownHostException;
/*
 * Main para los Procesos que formarán parte del algoritmo Suzuki-Kasami.
 *
 * v0.0.1
 * 28 nov 2017
 * Andrés Huerta
 * Felipe Vega
 */

public class MainProceso{

    public static void main(String[] args){
        Proceso process = new Proceso(Integer.parseInt(args[0]),
        Integer.parseInt(args[1]), Integer.parseInt(args[2]),
        Boolean.valueOf(args[3]));
    }
}
