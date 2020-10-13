JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Playfair.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	java Playfair encode WHITEHAT PLAYFIREXMBCDGHKNOQSTUVWZ

	java Playfair decode ZGRUMDPV PLAYFIREXMBCDGHKNOQSTUVWZ

	java Playfair encode AGOODFOODBOOKISACOOKBOOK PLAYFIREXMBCDGHKNOQSTUVWZ

	java Playfair decode YDQEQGASQGDKVTMKLDQEVTDKVT PLAYFIREXMBCDGHKNOQSTUVWZ

	java Playfair encode JIMJAMESJACK QWERTYUIOPASDFGHKLZXCVBNM

	java Playfair decode PLPBYDBTDUSVVN QWERTYUIOPASDFGHKLZXCVBNM
