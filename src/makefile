JFLAGS = -g
#-Xlint
JC = javac
JVM = java
PUERTO = 12345
POLITICA = -Djava.security.policy
ARCHIVO = permisos
DIRECCION = localhost

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	SemaforoInter.java \
	Token.java \
	Proceso.java \
	mainProceso.java \
	servidorRMI.java \

MAIN = servidorRMI

#default: classes
#	$(JVM) $(MAIN)

classes: $(CLASSES:.java=.class)

run: classes
ifeq ($(MAIN),Proceso)
	$(JVM) $(POLITICA)=$(ARCHIVO) $(MAIN) $(DIRECCION) $(PUERTO)
else
	$(JVM) $(POLITICA)=$(ARCHIVO) $(MAIN) $(PUERTO)
endif

clean:
	$(RM) *.class
