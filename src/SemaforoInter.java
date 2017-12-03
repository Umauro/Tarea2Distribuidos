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

 import java.rmi.Remote;
 import java.rmi.RemoteException;

 /*
  * Interfaz de semáforos que utilizarán Java RMI
  *
  * v0.0.1
  * 28 nov 2017
  * Andrés Huerta
  * Felipe Vega
  */

public interface SemaforoInter extends Remote {
    void request(int id, int seq) throws RemoteException;
    Token waitToken(int id) throws RemoteException;
    void takeToken(Token token) throws RemoteException;
    void kill() throws RemoteException;
}
