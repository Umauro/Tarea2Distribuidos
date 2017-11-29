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

import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.*;
 /*
  * Procesos que formarán parte del algoritmo Suzuki-Kasami.
  *
  * v0.0.1
  * 28 nov 2017
  * Andrés Huerta
  * Felipe Vega
  */

public class Proceso{
    public static class multicastListener implements Runnable{
        public MulticastSocket socketM;
        public InetAddress addressM;
        public int puertoM;
        public byte[] buf;

        public multicastListener(){
            try{
                addressM = InetAddress.getByName("230.0.0.1");
                puertoM = 4444;
                socketM = new MulticastSocket(puertoM);
            }
            catch(UnknownHostException e){
                System.err.println("Error al asignar ip Multicast");
                e.printStackTrace();
                System.exit(1);
            }
            catch(IOException e){
                System.err.println("Error al crear Socket Multicast");
                e.printStackTrace();
                System.exit(1);
            }
            buf = new byte[256];
        }
        public void run(){
            System.out.println("hola");
        }
    }
    public static void main(String[] args){
        int id = Integer.parseInt(args[0]);
        int cantidadProcesos = Integer.parseInt(args[1]);
        int delayTime = Integer.parseInt(args[2]);
        Boolean bearer = Boolean.valueOf(args[3]);
        Boolean haveToken = false;
        Token token = null;

        if(bearer){
            token = new Token();
            haveToken = true;
        }

        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            SemaforoInter inter = (SemaforoInter) Naming.lookup("//"+ args[0]
            +":"+args[1]+"/SK");
            inter.waitToken();
        }
        catch(RemoteException e){
            System.err.println("Error: " + e.toString());
        }
        catch (Exception e){
            System.err.println("Excepción: ");
            e.printStackTrace();
        }
    }
}
