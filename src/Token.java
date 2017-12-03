/*
 * Tarea 2 Sistemas Distribuidos 2017-2
 *
 * Primera versión de la tarea para la implementación de semaforos distribuidos,
 * utilizando el algoritmo Suzuki-Kasami, en conjunto con Java RMI.
 * Desarollada por Andrés Huerta (@MeatBoyUSM) y Felipe Vega (@Umauro)
 *
 * 28/11/2017
 *
 * INSERTAR LO DEL COPYWEA ACÁ
 */

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.net.UnknownHostException;

/*
 * Token utilizado por el algoritmo Suzuki-Kasami.
 *
 * v0.0.1
 * 28 nov 2017
 * Andrés Huerta
 * Felipe Vega
 */

public class Token extends UnicastRemoteObject implements InterfaceRMI{
    /* Cola de procesos en espera del Token */
    Queue<Integer> colaProcesos;
    Vector<Integer> listaProcesos;
    int procesoActual;
    /* Constructor de la clase Token */
    public Token() throws RemoteException{
        colaProcesos = new LinkedList();
        listaProcesos = new Vector();
        procesoActual = -1;
    }

    public void inicializarToken(int n, int id) throws RemoteException{
        procesoActual = id;
        for(int i = 0; i<n; i++){
            listaProcesos.add(0);
        }
    }

    public void request(int id, int seq) throws RemoteException{
        System.out.println("LLamando a la función Request");
    }

    public void waitToken() throws RemoteException{
        System.out.println("Llamando a la función waitToken");
    }

    public void takeToken(Token token) throws RemoteException{
        System.out.println("Llamando a la funcón takeToken");
    }

    public void kill() throws RemoteException{
        System.exit(0);
    }

    public static void main(String[] args){
       if(System.getSecurityManager() == null){
           System.setSecurityManager(new RMISecurityManager());
       }

       try{
           InterfaceRMI inter = new Token();
           Naming.rebind("rmi://localhost:" + args[0] + "/SK", inter);
       }
       catch (RemoteException e){
           System.err.println("Error: " + e.toString());
           System.exit(1);
       }
       catch (Exception e){
           System.err.println("Excepcion: ");
           e.printStackTrace();
           System.exit(1);
       }
   }

}
