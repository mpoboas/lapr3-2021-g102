main: fillMatrixFromFile.o totalnumber.o main.o
	gcc fillMatrixFromFile.o totalnumber.o main.o -o main

fillMatrixFromFile.o: fillMatrixFromFile.c
	gcc -g -Wall -c fillMatrixFromFile.c -o fillMatrixFromFile.o

totalnumber.o: totalnumber.s 
	gcc -g -Wall -c totalnumber.s -o  totalnumber.o

main.o: main.c totalnumber.h
	gcc -g -Wall -c main.c -o main.o

run: main
	./main

clean:
	rm totalnumber.o fillMatrixFromFile.o main.o main