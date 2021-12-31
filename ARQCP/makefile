main: fillMatrixFromFile.o totalnumber.o is_position_filled.o main.o
	gcc fillMatrixFromFile.o totalnumber.o is_position_filled.o main.o -o main

fillMatrixFromFile.o: fillMatrixFromFile.c
	gcc -g -Wall -c fillMatrixFromFile.c -o fillMatrixFromFile.o

totalnumber.o: totalnumber.s 
	gcc -g -Wall -c totalnumber.s -o  totalnumber.o

is_position_filled.o: is_position_filled.s 
	gcc -g -Wall -c is_position_filled.s -o  is_position_filled.o

main.o: main.c totalnumber.h is_position_filled.h
	gcc -g -Wall -c main.c -o main.o

run: main
	./main

clean:
	rm is_position_filled.o totalnumber.o fillMatrixFromFile.o main.o main