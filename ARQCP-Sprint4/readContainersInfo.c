#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "struct.h"
// percorre o ficheiro e o array de estruturas para inserir as posições
int getCargoManifestInformation( char *filename, container* array, int n){
    FILE *file = fopen(filename, "r");
    int i=-1;
    int got; // representa o numero de campos obtidos pela leitura do ficheiro
    do
    {
        i++;
        int id;
        char auxX,auxY,auxZ;
        got = fscanf(file, "%d,%hhd,%hhd,%hhd ", &id, &auxX, &auxY, &auxZ);
        //Percorre o array de containers inserindo a posição x,y,z no container com o mesmo id
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
// percorre o ficheiro que contêm a informação de containers e preenche array
container* getContainerGeneralInformation(container array[],char *filename, int *size){
    FILE *file = fopen(filename, "r"); // abre o ficheiro
    int i=0;
    int got; // representa o numero de campos obtidos pela leitura do ficheiro
    char firstLine[1024];
    fscanf(file,"%s",firstLine); // leitura do cabeçalho do ficheiro
    container *tmp; // apontador temporário usado no realloc
    do
    {

        if (i >= *(size)) // verifica se o indice ultrapassa o valor de elementos do array
        {
            *(size) = *(size) + 1; // incrementa o valor do tamanho do array
            tmp = (container*)realloc(array, *(size)*sizeof(container)); // aloca mais um container no array
            if (tmp == NULL) // Se ocorreu algum erro o programa sai
            {
                printf ("Unable to resize vector !\n");
                break ;
            }
            array = tmp;
        }
        // preenche o array com as informações
        got = fscanf(file, "%d,%s ,%f,%f,%f,%f ",&array[i].containerID, array[i].isoCode, &array[i].length, &array[i].width, &array[i].height, &array[i].exposedArea);
        i++;
        } while (got == 6);
    fclose(file);
    // retorna o array com o novo tamanho
    return array;
}

//percorre o ficheiro com os dados das paredes e insere nos containers
void addContainersWallInformation(container array[],int n,char *filename){
    FILE *file = fopen(filename, "r");
    int got; // representa o numero de campos obtidos pela leitura do ficheiro
    char firstLine[1024];
    fscanf(file,"%s",firstLine); // linha do cabeçalho
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

    //percorre o array de contentores e insere os dados das paredes no respetiva estrutura
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

// Aloca size estruturas
container* alloc_mem_for_arr(int size){
    container* array =  (container*)malloc(size * sizeof(container));
    return array;
}