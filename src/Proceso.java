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
 /*
  * Procesos que formarán parte del algoritmo Suzuki-Kasami.
  *
  * v0.0.1
  * 28 nov 2017
  * Andrés Huerta
  * Felipe Vega
  */

public class Proceso{
    /** Atributos del proceso principal **/
    public int id;
    public int cantidadProcesos;
    public int delayTime;
    public Boolean bearer;
    public Token token;
    public Boolean haveToken;
    public String estado;

    private MulticastSocket socketM;
    private InetAddress addressM;
    private int puertoM;
    private byte[] buf;


    public Proceso(int id, int cantidadProcesos, int delayTime, Boolean bearer){
            this.id = id;
            this.cantidadProcesos = cantidadProcesos;
            this.delayTime = delayTime;
            this.bearer = bearer;
            this.estado = "verde";
            if(this.bearer){
                this.token = new Token(cantidadProcesos);
                this.haveToken = true;

            }
            else{
                this.token = null;
                this.haveToken = false;

            }
    }

    public void multicastListener(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                buf = new byte[256];
                /** Inicialización del Socket Multicast **/
                try{
                    addressM = InetAddress.getByName("230.0.0.1");
                    puertoM = 4444;
                    socketM = new MulticastSocket(puertoM);
                    socketM.joinGroup(addressM);
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

                /** Escuchar Mensajes **/
                while(true){
                    try{
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        socketM.receive(packet);
                        String response = new String(packet.getData(),
                        packet.getOffset(), packet.getLength());
                        String[] parser = response.split(";");
                        if(haveToken){
                            System.out.println("Me llegó el request");
                            System.out.println("Voy a encolar el proceso con id " + parser[0]);
                            token.encolarProceso(Integer.parseInt(parser[0]));
                            /** Acá hay que manejar que se hace en el request **/
                        }
                    }
                    catch (SocketTimeoutException e){
                        System.err.println("Excepcion: ");
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        System.err.println("Excepcion: ");
                        e.printStackTrace();
                    }
                }

            }
        });
        t.start();
    }

    public void funcionalidades(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                if(System.getSecurityManager() == null){
                    System.setSecurityManager(new SecurityManager());
                }
                try{
                    //SemaforoInter inter = (SemaforoInter) Naming.lookup("//"+ args[0]
                    //+":"+args[1]+"/SK");
                    SemaforoInter inter = (SemaforoInter) Naming.lookup("//localhost:12345/SK");

                    /*Pedir el token una vez pasado el Delay Time*/
                    Thread.sleep(delayTime);
                    if(haveToken == false){

                        inter.request(id,1);
                        System.out.println("Voy a pedir el token con id" + id);
                        estado = "amarillo";
                        token = inter.waitToken(id);
                        System.out.println("Me llegó el token");
                        haveToken = true;
                    }
                    if(haveToken){
                        token.listaProcesos.set(id-1,1);
                        //Ruta Crítica va acá
                        System.out.println("Seccion Critica");
                        estado = "rojo";
                        Thread.sleep(2000);
                        //Termino Sección crítica
                        estado = "verde";
                        if(!token.colaRequest.isEmpty()){

                            System.out.println("Voy a enviar el Token");
                            inter.takeToken(token);
                            haveToken = false;
                            token = null;
                            System.exit(0);
                        }
                        else{
                            Boolean leDamos = true;
                            for(int i=0; i < token.listaProcesos.size(); i++){
                                if(token.listaProcesos.get(i) == 0){
                                    leDamos = false;
                                }
                            }
                            if(!leDamos){
                                while(token.colaRequest.isEmpty()){
                                    Thread.sleep(1);
                                }
                                inter.takeToken(token);
                                token = null;
                                System.exit(0);
                            }
                            else{
                                System.out.println("Todos terminaron, así que chaolovimoh");
                                inter.kill();
                                System.exit(0);
                            }
                        }
                    }

                }
                catch(RemoteException e){
                    System.err.println("Error: " + e.toString());
                }
                catch (Exception e){
                    System.err.println("Excepción: ");
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
