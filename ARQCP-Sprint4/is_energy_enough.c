#include <stdio.h>
#include "struct.h"
#include "get_needed_energy_container.h"



void is_energy_enough(float generatorEnergy, int hours, float outerTemperature, container *ptrContainer, int arraySize){
	
	
	float totalEnergyNecessary = 0; 
	int i;
	for(i = 0; i<arraySize; i++){
			//somatorio da energia necessaria para cada container refrigerado
			totalEnergyNecessary =+ get_needed_energy_container(hours, outerTemperature, ptrContainer, arraySize, (ptrContainer + i)->x, (ptrContainer + i)->y, (ptrContainer + i)->z);
		
	}
	if(generatorEnergy>totalEnergyNecessary){
		printf("The energy generated is enough! You have %f energy to spare.",generatorEnergy-totalEnergyNecessary);
	}else{
		printf("It is needed more %f units of energy", totalEnergyNecessary-generatorEnergy);
	}
	
}
	