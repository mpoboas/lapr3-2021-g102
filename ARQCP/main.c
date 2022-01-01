#include <stdio.h>
#include <stdlib.h>
#include "fillMatrixFromFile.h"
#include "totalnumber.h"
#include "is_position_filled.h"

int ***matrix;
int shipCapacityX;
int shipCapacityY;
int shipCapacityZ;
int x = 0;
int y = 0;
int z = 0;

int **matrixFromFile(char *fileDirectory, int shipCapacity){
    int numberOfParams = 4;
    int *p = calloc(shipCapacity * numberOfParams, sizeof(int));
    int *(*matrixPointer) = malloc(shipCapacity * sizeof(int *));
    for (int i = 0; i < shipCapacity; i++){
        *(matrixPointer + i) = p + i * numberOfParams;
    }
    fillMatrixFromFile(matrixPointer, fileDirectory);
    return matrixPointer;
}

void fillCargoManifestMatrix(int ***matrix, char *file, int shipCapacity){
    int **pointContentsFile = matrixFromFile(file, shipCapacity);
    fill3DMatrix(matrix, pointContentsFile, shipCapacity);
    free(pointContentsFile);
}

int ***allocateArray(int x, int y, int z){
    int ***matrix = (int ***)malloc(x * sizeof(int **));
    for (int i = 0; i < x; i++){
        matrix[i] = (int **)malloc(y * sizeof(int *));
        for (int j = 0; j < y; j++){
            matrix[i][j] = (int *)malloc(z * sizeof(int));
        }
    }
    return matrix;
}

void create3DArray(){
    char file[70];
    char *pointFile;
    pointFile = file;
    printf("Ship x axix?\n");
    scanf("%d", &shipCapacityX);
    printf("Ship y axix?\n");
    scanf("%d", &shipCapacityY);
    printf("Ship z axix?\n");
    scanf("%d", &shipCapacityZ);
    matrix = allocateArray(shipCapacityX, shipCapacityY, shipCapacityZ);
    printf("File?\n");
    scanf("%s", pointFile);
    fillCargoManifestMatrix(matrix, pointFile, shipCapacityX * shipCapacityY * shipCapacityZ);
}

int main(void){
    create3DArray();

    int is_filled = is_position_filled();
    printf("Is position filled: %d\n", is_filled);

    long res = number_occupied_free();
    long *ptr = &res;
    int *x = (int *)ptr;
    printf("Occupied: %d || Free: %d\n", *(x + 0), *(x + 1));

    return 0;
}