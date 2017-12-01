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
  * Servidor que contendrá los métodos a utilizar con Java RMI
  *
  * v0.0.1
  * 28 nov 2017
  * Andrés Huerta
  * Felipe Vega
  */

 public class ServidorRMI extends UnicastRemoteObject implements SemaforoInter {
     public MulticastSocket socketM;
     public InetAddress addressM;
     public int puertoM;
     public byte[] buf;

     ServidorRMI() throws RemoteException{
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

     public void request(int id, int seq) throws RemoteException{
         try{
             buf = (String.valueOf(id) + ";"+ String.valueOf(seq)).getBytes();
             DatagramPacket packet = new DatagramPacket(buf, buf.length,addressM,puertoM);
             try{
                 socketM.send(packet);
                 System.out.println("Mandé el paquetito c:");
             }
             catch (IOException e){
                 e.printStackTrace();
             }
         }
         catch(Exception e){
             System.err.println("ME CAÍ");
             e.printStackTrace();
             System.exit(1);
         }



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
            SemaforoInter inter = new ServidorRMI();
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
