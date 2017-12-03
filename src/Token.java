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
    public Queue<Integer> colaProcesos;
    public Vector<Integer> listaProcesos;
    public int procesoActual;

    public InetAddress addressM;
    public MulticastSocket socketM;
    public byte[] buf;

    /* Constructor de la clase Token */
    public Token() throws RemoteException{
        colaProcesos = new LinkedList();
        listaProcesos = new Vector();
        procesoActual = -1;
        try{
            addressM = InetAddress.getByName("230.0.0.1");
            socketM = new MulticastSocket(4444);
        }
        catch(UnknownHostException e){
            System.err.println("Error al iniciar Socket Multicast");
            e.printStackTrace();
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Error al iniciar Socket Multicast");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void inicializarToken(int n, int id) throws RemoteException{
        procesoActual = id;
        for(int i = 0; i<n; i++){
            listaProcesos.add(0);
        }
    }

    public void request(int id, int seq) throws RemoteException{
        System.out.println("LLamando a la función Request");
        Thread t = new Thread(new Runnable(){
            public void run(){
                try{
                    buf = ("request;"+String.valueOf(id)+";"+String.valueOf(seq)).getBytes();
                    DatagramPacket packet = new DatagramPacket(buf,buf.length,addressM, 4444);
                    try{
                        socketM.send(packet);
                        System.out.println("Envié el Request");
                    }
                    catch (IOException e){
                        System.err.println("No pude enviar el paquete");
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
                catch(Exception e){
                    System.err.println("Me caí");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
        t.start();
    }

    public void waitToken() throws RemoteException{
        System.out.println("Llamando a la función waitToken");
    }

    public void takeToken(Token token) throws RemoteException{
        System.out.println("Llamando a la función takeToken");
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
