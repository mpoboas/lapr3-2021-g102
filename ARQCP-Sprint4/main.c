#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "struct.h"
#include "get_needed_energy_container.h"
#include "readContainersInfo.h"



int main(void) {
    //-------------- Ficheiro Manifest
    char fileManifest[] = "manifest.txt";
    char *filename1;
    filename1 = fileManifest;
    //printf("File?\n");
    //scanf("%s", filename);


    //-------------- Ficheiro Container General
    char fileContainers[70];
    char *filename2;
    filename2 = fileContainers;
    printf("File?\n");
    scanf("%s", filename2);

    //-------------- Ficheiro Container Walls
    char fileContainerInfo[] = "containersWallInfo.csv";

    int arraySize = 5;

    container *containers;
    containers = alloc_mem_for_arr(arraySize);

    containers = getContainerGeneralInformation(containers, filename2,&arraySize);

    getCargoManifestInformation(filename1, containers,arraySize);
    addContainersWallInformation(containers, arraySize,  fileContainerInfo);


    for (int j = 0; j < arraySize; j++)
    {
        /*
        printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | x: %d | y: %d | z: %d \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea,
                            containers[j].x, containers[j].y, containers[j].z);*/


        printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | MI: %s | MC: %f | MT: %f | CM: %s | CC: %f | CT: %f | OM: %s | OC: %f | OT: %f \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea,
                                           containers[j].innerWallMaterial, containers[j].innerWallCondutivity, containers[j].innerWallThickness,
                                           containers[j].centerWallMaterial, containers[j].centerWallCondutivity, containers[j].centerWallThickness,
                                           containers[j].outerWallMaterial, containers[j].outerWallCondutivity, containers[j].centerWallThickness);
    }
    
    float tripDuration = 1;
    float outerTemperature = 20;
    int containerPosX = 0;
    int containerPosY = 0;
    int containerPosZ = 0;
    printf("Trip duration? ");
    scanf("%f", &tripDuration);
    printf("Outer temperature? ");
    scanf("%f", &outerTemperature);
    printf("Container position:\n");
    printf("X? ");
    scanf("%d", &containerPosX);
    printf("Y? ");
    scanf("%d", &containerPosY);
    printf("Z? ");
    scanf("%d", &containerPosZ);

    float res = get_needed_energy_container(tripDuration, outerTemperature, containers, arraySize, containerPosX, containerPosY, containerPosZ);
    printf("Total needed energy: %f J\n", res);
    
    return 0;
}