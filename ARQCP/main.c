#include <stdio.h>
#include <stdlib.h>
#include "fillMatrixFromFile.h"

int** matrixFromFile(char *fileDirectory,int shipCapacity ){
    int *p =  calloc(shipCapacity*4, sizeof(int));
    int *(*matrixPointer) = malloc(shipCapacity*sizeof(int*));
    for(int i = 0; i< shipCapacity; i++){
        *(matrixPointer+i)= p + i*4;
    }
    fillMatrixFromFile(matrixPointer, fileDirectory);
    return matrixPointer;
}

int main(void){










    return 0;
}