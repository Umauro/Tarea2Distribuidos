id=1
n=1
initialDelay=0
bearer=true
puerto=12345

JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: clases

clases: $(classes)

proceso:
	java -Djava.security.policy=permisos MainProceso $(id) $(n) $(initialDelay) $(bearer)

run:
	java -Djava.security.policy=permisos ServidorRMI $(puerto)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
