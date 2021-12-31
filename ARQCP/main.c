#include <stdio.h>
#include <stdlib.h>
#include "fillMatrixFromFile.h"
#include "totalnumber.h"
int ***matrix;
int** matrixFromFile(char *fileDirectory,int shipCapacity ){
    int numberOfParams = 4;
    int *p =  calloc(shipCapacity*numberOfParams, sizeof(int));
    int *(*matrixPointer) = malloc(shipCapacity*sizeof(int*));
    for(int i = 0; i< shipCapacity; i++){
        *(matrixPointer+i)= p + i*numberOfParams;
    }
    fillMatrixFromFile(matrixPointer, fileDirectory);
    return matrixPointer;
}

void fillCargoManifestMatrix(int ***matrix, char* file, int shipCapacity){
    int **pointContentsFile = matrixFromFile(file, shipCapacity);
    fill3DMatrix(matrix,pointContentsFile,shipCapacity);
}

int ***allocateArray(int x,int y, int z){
    int*** matrix = (int***)malloc(x * sizeof(int**));
    for (int i = 0; i < x; i++)
    {
        matrix[i] = (int**)malloc(y * sizeof(int*));
        for (int j = 0; j < y; j++)
        {
            matrix[i][j] = (int*)malloc(z * sizeof(int));
        }
    }
    return matrix;
}
int main(void){
    int shipCapacityX;
    int shipCapacityY;
    int shipCapacityZ;
    char file[70];
    char *pointFile;
    pointFile = file;
    printf("Ship x axix?");
    scanf("%d",&shipCapacityX);
    printf("Ship y axix?");
    scanf("%d",&shipCapacityY);
    printf("Ship z axix?");
    scanf("%d",&shipCapacityZ);
    matrix = allocateArray(shipCapacityX, shipCapacityY,  shipCapacityZ);
    printf("File?");
    scanf("%s",pointFile);
    fillCargoManifestMatrix(matrix, pointFile, shipCapacityX * shipCapacityY * shipCapacityZ);




    
    long res = number_occupied_free();
    long *ptr = &res;
    int *x = (int *)ptr;
    printf("Occupied: %d || Free: %d\n", *(x+0),*(x+1));



    return 0;
}