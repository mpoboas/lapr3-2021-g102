main: main.o is_refrigerated.o get_needed_energy_container.o readContainersInfo.o 
	gcc main.o is_refrigerated.o get_needed_energy_container.o readContainersInfo.o -o main

main.o: main.c is_refrigerated.h get_needed_energy_container.h
	gcc -g -Wall -c main.c -o main.o

is_refrigerated.o: is_refrigerated.s
	gcc -Wall -g -c is_refrigerated.s -o is_refrigerated.o

get_needed_energy_container.o: get_needed_energy_container.c
	gcc -Wall -g -c get_needed_energy_container.c -o get_needed_energy_container.o
	
readContainersInfo.o: readContainersInfo.c
	gcc -Wall -g -c readContainersInfo.c -o readContainersInfo.o

run: main
	./main

clean:
	rm main.o main