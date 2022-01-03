main: fillMatrixFromFile.o totalnumber.o is_position_occupied.o main.o
	gcc fillMatrixFromFile.o totalnumber.o is_position_occupied.o main.o -o main

fillMatrixFromFile.o: fillMatrixFromFile.c
	gcc -g -Wall -c fillMatrixFromFile.c -o fillMatrixFromFile.o

totalnumber.o: totalnumber.s 
	gcc -g -Wall -c totalnumber.s -o  totalnumber.o

is_position_occupied.o: is_position_occupied.s 
	gcc -g -Wall -c is_position_occupied.s -o  is_position_occupied.o

main.o: main.c totalnumber.h is_position_occupied.h
	gcc -g -Wall -c main.c -o main.o

run: main
	./main

clean:
	rm is_position_occupied.o totalnumber.o fillMatrixFromFile.o main.o main