#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "struct.h"
#include "is_refrigerated.h"
#include "get_needed_energy_container.h"
#include "readContainersInfo.h"
#include "is_energy_enough.h"

int main(void) {
    //-------------- Ficheiro Manifest
    //char fileManifest[] = "manifest.txt";
    char fileManifest[70];
    char *filename1;
    filename1 = fileManifest;
    printf("File cargo manifest (manifest.txt)?\n");
    scanf("%s", filename1);


    //-------------- Ficheiro Container General
    char fileContainers[70];
    char *filename2;
    filename2 = fileContainers;
    printf("File with containers general info (containersGeneral.txt)?\n");
    scanf("%s", filename2);

    //-------------- Ficheiro Container Walls
    //char fileContainerInfo[] = "containersWallInfo.csv";
    char fileContainerInfo[70];
    char *filename3;
    filename3 = fileContainerInfo;
    printf("File with containers wall information (containersWallInfo.txt)?\n");
    scanf("%s", filename3);

    int arraySize = 5; // representa o tamanho do array de contentores inicializado a 5

    container *containers; // array de estruturas
    containers = alloc_mem_for_arr(arraySize); // aloca memoria para array inicial

    containers = getContainerGeneralInformation(containers, filename2,&arraySize); // preenche array de containers com a informação geral, aumentando de tamanho dinamicamente

    getCargoManifestInformation(filename1, containers,arraySize); // preenche as estruturas com a informação das posições
    addContainersWallInformation(containers, arraySize,  fileContainerInfo); // preenche as estruturas com as informação das paredes (materiais/condutividades/espessuras)

    for (int j = 0; j < arraySize; j++)
    {
        
        /*printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | x: %d | y: %d | z: %d \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea,
                            containers[j].x, containers[j].y, containers[j].z);


        printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | MI: %s | MC: %f | MT: %f | CM: %s | CC: %f | CT: %f | OM: %s | OC: %f | OT: %f \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea,
                                           containers[j].innerWallMaterial, containers[j].innerWallCondutivity, containers[j].innerWallThickness,
                                           containers[j].centerWallMaterial, containers[j].centerWallCondutivity, containers[j].centerWallThickness,
                                           containers[j].outerWallMaterial, containers[j].outerWallCondutivity, containers[j].centerWallThickness);*/
    }
    
    float tripDuration = 1;
    float outerTemperature = 20;
    char containerPosX = 0;
    char containerPosY = 0;
    char containerPosZ = 0;
    printf("Trip duration? ");
    scanf("%f", &tripDuration);
    printf("Outer temperature? ");
    scanf("%f", &outerTemperature);
    printf("Container position:\n");
    printf("X? ");
    scanf(" %hhd", &containerPosX);
    printf("Y? ");
    scanf(" %hhd", &containerPosY);
    printf("Z? ");
    scanf(" %hhd", &containerPosZ);

    int isRefrigerated = is_refrigerated(containers, containerPosX, containerPosY, containerPosZ, arraySize);
    if (isRefrigerated == 0) printf("The container in position [%hhd, %hhd, %hhd] is not refrigerated.\n", containerPosX, containerPosY, containerPosZ);
    else if (isRefrigerated == 1) printf("The container in position [%hhd, %hhd, %hhd] is refrigerated.\n", containerPosX, containerPosY, containerPosZ);
    else printf("No container was found in position [%hhd, %hhd, %hhd].\n", containerPosX, containerPosY, containerPosZ);

    float res = get_needed_energy_container(tripDuration, outerTemperature, containers, arraySize, containerPosX, containerPosY, containerPosZ);
    if (res != -1) printf("Total needed energy: %f J\n", res);
	
	float generatorEnergy = 0;
	printf("Generator energy?");
	scanf("%f", &generatorEnergy);
	is_energy_enough(generatorEnergy, tripDuration, outerTemperature, containers, arraySize)
    
    return 0;
}