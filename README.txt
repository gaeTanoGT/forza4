https://youtu.be/MyhiBldT0c0
	-javac -h . JNI.java

2:26  -->  indicare nome nelle funzioni

4:19  -->  compilare da visual studio
			- creare un nuovo DLL project
			- inserire il .h file e il .cPP file creati dal primo tutorial nelle corrispettive cartelle (vedi png)
			- rimuovere il file .h dal progetto java
			- selezionare compilazione x64
			- seguire i settaggi del progetto del tutorial: https://youtu.be/Hw7563ojRbU 	--> 2:26 - 3:08 **
			- compilare con debugger windows locale x64 bit
			- inserire il .dll file in una directory del progetto java e trascrivere la directory nel .java file

** path utilizzati:
	- C:\Program Files\Java\jdk-19\include
	- C:\Program Files\Java\jdk-19\include\win32
	- *esempio, da modificare... ||cartella src del progetto java contenente il richiamo dei metodi nativi|| es:--> C:\Users\gaeta\Documents\java\javaC\src\javaC


HOWTO: spiega cosa fanno i progetti