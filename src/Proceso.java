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
      public Boolean haveToken;
      public int id;
      public int delayTime;

      public static void main(String[] args){
          if(System.getSecurityManager() == null){
              System.setSecurityManager(new SecurityManager());
          }
          try{
              SemaforoInter inter = (SemaforoInter) Naming.lookup("//"+ args[0]+":"+args[1]+"/SK");
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
