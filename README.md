# Tarea2Distribuidos
Tarea 2 para el ramo Sistemas Distribuidos

## Integrantes

- **Andres Huerta** 201473544-8
- **Felipe Vega**   201473511-1

## Supuestos

- **El proceso que parte con el token se inicializa primero**

## Instrucciones de Compilación

Para compilar, ingresar a la carpeta src y ejecutar el siguiente comando

~~~
make
~~~

## Instrucciones de Ejecución

#### Importante: El rmi solo correrá en el puerto 12345 :(. Crear carpeta "logs" si no está en la raiz.

- Iniciar el servicio RMI
~~~
rmiregistry 12345 &
~~~    
- Para ejecutar el ServidorRMI
~~~
make run
~~~
- Para ejecutar los Procesos
~~~
make proceso id=<id> n=<cantidadProcesos> initialDelay=<delay> bearer=<bearer>
.
#Ejemplo
.
make proceso id=1 n=2 initialDelay=2000 bearer=true
~~~
- Si no se indican los parámetros por defecto se tiene
~~~
n=1, id=1, initialDelay=0, bearer=true
~~~
