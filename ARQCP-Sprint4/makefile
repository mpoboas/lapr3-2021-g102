main: main.o get_needed_energy_container.o readContainersInfo.o 
	gcc main.o get_needed_energy_container.o readContainersInfo.o -o main

main.o: main.c get_needed_energy_container.h
	gcc -g -Wall -c main.c -o main.o

get_needed_energy_container.o: get_needed_energy_container.c
	gcc -Wall -g -c get_needed_energy_container.c -o get_needed_energy_container.o
	
readContainersInfo.o: readContainersInfo.c
	gcc -Wall -g -c readContainersInfo.c -o readContainersInfo.o

run: main
	./main

clean:
	rm main.o main