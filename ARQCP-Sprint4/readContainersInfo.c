#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "struct.h"
int getCargoManifestInformation( char *filename, container* array, int n){
    FILE *file = fopen(filename, "r");
    int i=-1;
    int got;
    do
    {
        i++;
        int id;
        char auxX,auxY,auxZ;
        got = fscanf(file, "%d,%hhd,%hhd,%hhd ", &id, &auxX, &auxY, &auxZ);
        for (int j = 0; j < n; j++){
           if (array[j].containerID == id){
               array[j].x=auxX;
               array[j].y=auxY;
               array[j].z=auxZ;
                break;
           }
        }
        
    } while (got == 4);
    fclose(file);
    return i;
}

container* getContainerGeneralInformation(container array[],char *filename, int *size){
    FILE *file = fopen(filename, "r");
    int i=0;
    int got;
    char firstLine[1024];
    fscanf(file,"%s",firstLine);
    container *tmp;
    do
    {

        if (i >= *(size))
        {
            *(size) = *(size) + 1;
            tmp = (container*)realloc(array, *(size)*sizeof(container));
            if (tmp == NULL)
            {
                printf ("Unable to resize vector !\n");
                break ;
            }
            array = tmp;
        }
        got = fscanf(file, "%d,%s ,%f,%f,%f,%f ",&array[i].containerID, array[i].isoCode, &array[i].length, &array[i].width, &array[i].height, &array[i].exposedArea);
        i++;
        } while (got == 6);
    fclose(file);
    return array;
}

//Not working properly
void addContainersWallInformation(container array[],int n,char *filename){
    FILE *file = fopen(filename, "r");
    int got;
    char firstLine[1024];
    fscanf(file,"%s",firstLine);
    do
    {
        int id;
        char innerWallM[16];
        float innerWallC, innerWallT;
        char centerWallM[16];
        float centerWallC,centerWallT;
        char outerWallM[16];
        float outerWallC,outerWallT;
        got = fscanf(file, "%d,%s ,%f,%f,%s ,%f,%f,%s ,%f,%f ",&id,innerWallM, &innerWallC, &innerWallT,
                                                    centerWallM, &centerWallC, &centerWallT,
                                                    outerWallM, &outerWallC, &outerWallT);

        for (int i = 0; i < n; i++){
            if (array[i].containerID == id)
            {   
                strcpy(array[i].innerWallMaterial,innerWallM);
                array[i].innerWallCondutivity=innerWallC;
                array[i].innerWallThickness=innerWallT;
                strcpy(array[i].centerWallMaterial,centerWallM);
                array[i].centerWallCondutivity=centerWallC;
                array[i].centerWallThickness=centerWallT;
                strcpy(array[i].outerWallMaterial,outerWallM);
                array[i].outerWallCondutivity=outerWallC;
                array[i].outerWallThickness=outerWallT;
                break;
            }
        }
    } while (got == 10);
    fclose(file);
}

container* alloc_mem_for_arr(int size){
    container* array =  (container*)malloc(size * sizeof(container));
    return array;
}