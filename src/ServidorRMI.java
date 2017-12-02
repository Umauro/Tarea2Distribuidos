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
     //** Socket Multicast para Request **//
     public MulticastSocket socketM;
     public MulticastSocket socketM2;
     public InetAddress addressM;
     public InetAddress addressM2;
     public int puertoM;
     public int puertoM2;
     public byte[] buf;
     public byte[] buf2;

     ServidorRMI() throws RemoteException{
         try{
             addressM = InetAddress.getByName("230.0.0.1");
             addressM2 = InetAddress.getByName("231.0.0.1");
             puertoM = 4444;
             puertoM2 = 4445;
             socketM = new MulticastSocket(puertoM);
             socketM2 = new MulticastSocket(puertoM2);
             socketM2.setSoTimeout(500);
             socketM2.joinGroup(addressM2);

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
         buf2 = new byte[256];
     }

     public void request(int id, int seq) throws RemoteException{
         Thread t = new Thread(new Runnable(){
             public void run(){
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
         });
         t.start();

     }

     public Token waitToken(int id) throws RemoteException{
         final Token token = new Token();
         Thread t = new Thread(new Runnable(){
             public void run(){
                 while(true){
                     try{
                         DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length);
                         System.out.println("Esperando el Token");
                         socketM2.receive(packet2);
                         try{
                             ByteArrayInputStream serializado = new ByteArrayInputStream(buf2);
                             ObjectInputStream is = new ObjectInputStream(serializado);
                             Token tokenAux = (Token)is.readObject();
                             is.close();
                             if(id == tokenAux.getProxId()){
                                 token.listaProcesos = tokenAux.listaProcesos;
                                 token.colaRequest = tokenAux.colaRequest;
                                 token.proxId = tokenAux.proxId;
                                 break;
                             }

                         }
                         catch (IOException e){
                             e.printStackTrace();
                         }
                         catch (ClassNotFoundException e){
                             e.printStackTrace();
                         }
                     }
                     catch (SocketTimeoutException e){

                     }
                     catch(Exception e){
                         System.err.println("ME CAÍ en WaitToken");
                         e.printStackTrace();
                         System.exit(1);
                     }
                 }
             }
         });
         t.start();
         try{
            t.join();
         }
         catch (InterruptedException e){
             e.printStackTrace();
         }
         return token;
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
