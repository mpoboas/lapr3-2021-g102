main: fillMatrixFromFile.o main.o
	gcc fillMatrixFromFile.o main.o -o main

fillMatrixFromFile.o: fillMatrixFromFile.s
	gcc -g -Wall -c fillMatrixFromFile.s -o fillMatrixFromFile.o

main.o: main.c
	gcc -g -Wall -c main.c -o main.o

run: main
	./main

clean:
	rm fillMatrixFromFile.o main.o main