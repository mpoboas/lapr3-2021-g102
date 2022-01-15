#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
    int containerID;
    char isoCode[5];
    char x;
    char y;
    char z;

    float length;
    float width;
    float height;

    float exposedArea;

    char innerWallMaterial[16];
    float innerWallCondutivity;
    float innerWallThickness;

    char centerWallMaterial[16] ;
    float centerWallCondutivity;
    float centerWallThickness;
    
    char outerWallMaterial[16];
    float outerWallCondutivity;
    float outerWallThickness;

}container;

int getCargoManifestInformation( char *filename, container array[], int n){
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
    addContainersWallInformation(containers, 31,  "containersWallInfo.csv");


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


    return 0;
}