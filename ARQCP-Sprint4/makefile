main: main.o is_refrigerated.o get_needed_energy_container.o readContainersInfo.o is_energy_enough.o
	gcc main.o is_refrigerated.o get_needed_energy_container.o readContainersInfo.o is_energy_enough.o -o main

main.o: main.c is_refrigerated.h get_needed_energy_container.h is_energy_enough.h
	gcc -g -Wall -c main.c -o main.o

is_refrigerated.o: is_refrigerated.s
	gcc -Wall -g -c is_refrigerated.s -o is_refrigerated.o

get_needed_energy_container.o: get_needed_energy_container.c
	gcc -Wall -g -c get_needed_energy_container.c -o get_needed_energy_container.o
	
readContainersInfo.o: readContainersInfo.c
	gcc -Wall -g -c readContainersInfo.c -o readContainersInfo.o
	
is_energy_enough.o: is_energy_enough.c
	gcc -Wall -g -c is_energy_enough.c -o is_energy_enough.o

run: main
	./main

clean:
	rm main.o main