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
import java.net.UnknownHostException;



public class Proceso{
/** Atributos del proceso principal **/
    public int id;
    public int cantidadProcesos;
    public int delayTime;
    public Boolean bearer;
    public Boolean haveToken;
    public int estado;
    public InterfaceRMI inter;

    private MulticastSocket socketM;
    private InetAddress addressM;
    private int puertoM;
    private byte[] buf;


    public Proceso(int id, int cantidadProcesos, int delayTime, Boolean bearer){
        this.id = id;
        this.cantidadProcesos = cantidadProcesos;
        this.delayTime = delayTime;
        this.bearer = bearer;
        this.estado = 0;

        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            inter = (InterfaceRMI) Naming.lookup("//localhost:12345/SK");
        }
        catch (NotBoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            System.exit(1);
        }
        catch (RemoteException e){
            e.printStackTrace();
            System.exit(1);
        }
        if(this.bearer){
            this.haveToken = true;
        }
        else{
            this.haveToken = false;
        }
  }
}
