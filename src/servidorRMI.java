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

 /*
  * Servidor que contendrá los métodos a utilizar con Java RMI
  *
  * v0.0.1
  * 28 nov 2017
  * Andrés Huerta
  * Felipe Vega
  */

 public class servidorRMI extends UnicastRemoteObject implements SemaforoInter {

     servidorRMI() throws RemoteException{
         System.out.println("hola");
     }

     public void request(int id, int seq) throws RemoteException{
         System.out.println("Request");
     }

     public void waitToken() throws RemoteException{
         System.out.println("waitea");
     }

     public void takeToken(Token token) throws RemoteException{
         System.out.println("tomar token");
     }

     public void kill() throws RemoteException{
         System.out.println("DIEDIEDIE");
         System.exit(0);
     }

     public static void main(String[] args){
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }

        try{
            SemaforoInter inter = new servidorRMI();
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
