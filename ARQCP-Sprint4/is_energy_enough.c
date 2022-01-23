#include <stdio.h>
#include "get_needed_energy_container.h"
#include "get_refrigeration.h"

void is_energy_enough(float generatorEnergy, int hours, float outerTemperature, container *ptrContainers, int arraySize){
	
	int contRefrigeratedContainers = 0;
	float totalEnergyNecessary = 0;
	int i;
	for(i = 0; i<arraySize; i++){
		char isRefrigerated = getRefrigerated(ptrContainers + i);//ajustar para a função de assembly 
		if(isRefrigerated!=0){
			//somatorio da energia necessaria para cada container refrigerado
			totalEnergyNecessary =+ get_needed_energy_container(hours, outerTemperature, ptrContainers, (ptrContainer + i)->x), (ptrContainer + i)->y), (ptrContainer + i)->z);
		}
	}
	if(generatorEnergy>totalEnergyNecessary){
		printf("The energy generated is enough! You have %f energy to spare.",generatorEnergytotal-EnergyNecessary);
	}else{
		printf("It is needed more %f units of energy", totalEnergyNecessary-generatorEnergy);
	}
	
}
	