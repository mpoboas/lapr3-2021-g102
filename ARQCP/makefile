main: fillMatrixFromFile.o totalnumber.o main.o
	gcc fillMatrixFromFile.o main.o -o main

fillMatrixFromFile.o: fillMatrixFromFile.s
	gcc -g -Wall -c fillMatrixFromFile.s -o fillMatrixFromFile.o

totalnumber.o: totalnumber.s totalnumber.h
    gcc -g -Wall -c totalnumber.s

main.o: main.c totalnumber.h
	gcc -g -Wall -c main.c -o main.o

run: main
	./main

clean:
	rm fillMatrixFromFile.o main.o main