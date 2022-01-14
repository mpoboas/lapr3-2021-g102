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
    
    char outterWallMaterial[16];
    float outterWallCondutivity;
    float outterWallThickness;

}container;

int getCargoManifestInformation(int fileInfo[][4], char *filename, container array[], int n){
    FILE *file = fopen(filename, "r");
    int i=-1;
    int got;
    do
    {
        i++;
        got = fscanf(file, "%d,%d,%d,%d ", &fileInfo[i][0], &fileInfo[i][1],&fileInfo[i][2],&fileInfo[i][3]);
        for (int j = 0; j < n; j++){
            if (array[j].containerID == fileInfo[i][0])
            {   
                array[j].x=fileInfo[i][1];
                array[j].y=fileInfo[i][2];
                array[j].z=fileInfo[i][3];
                break;
            }
            
        }
        
    } while (got == 4);
    fclose(file);
    return i;
}

int getContainerGeneralInformation(container array[],char *filename){
    FILE *file = fopen(filename, "r");
    int i=-1;
    int got;
    char firstLine[1024];
    fscanf(file,"%s",&firstLine);
    do
    {
        i++;
        got = fscanf(file, "%d,%s ,%f,%f,%f,%f ",&array[i].containerID, &array[i].isoCode, &array[i].length, &array[i].width, &array[i].height, &array[i].exposedArea);
        
        } while (got == 6);
    fclose(file);
    return i;
}

//Not working properly
void addContainersWallInformation(container array[],int n,char *filename){
    FILE *file = fopen(filename, "r");
    int got;
    char firstLine[1024];
    fscanf(file,"%s",&firstLine);
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
                strcpy(array[i].outterWallMaterial,outerWallM);
                array[i].outterWallCondutivity=outerWallC;
                array[i].centerWallThickness=outerWallT;
                break;
            }
            
        }
        
    } while (got == 10);
    fclose(file);

}

int main(void) {
    char fileN[] = "manifest.txt";
    char *filename;
    filename = fileN;
    //printf("File?\n");
    //scanf("%s", filename);
    int shipCapacity;
    printf("Total Ship Capacity?\n");
    scanf("%d", &shipCapacity);
    int fileInfo[shipCapacity][4];

    //int quantidade = getCargoManifestInformation(fileInfo, filename);

    //printf("Quantidade = %d\n", quantidade);
    //for (int j = 0; j <= quantidade; j++)
    //{
    //    printf("%d,%d,%d,%d\n", fileInfo[j][0], fileInfo[j][1],fileInfo[j][2],fileInfo[j][3]);
    //}
    


    char fileNCon[70];
    char *filename2;
    filename2 = fileNCon;
    printf("File?\n");
    scanf("%s", filename2);
    container containers[shipCapacity];
    
    char fileNConI[] = "containersWallInfo.csv";
    int quantidadeC = getContainerGeneralInformation(containers, filename2);
    getCargoManifestInformation(fileInfo, filename, containers, quantidadeC);
    addContainersWallInformation(containers, quantidadeC,  fileNConI);


    printf("Quantidade = %d\n", quantidadeC);
    printf("1: %d\n", containers[29].containerID);

    for (int j = 0; j < quantidadeC; j++)
    {
        printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | x: %d | y: %d | z: %d \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea,
                            containers[j].x, containers[j].y, containers[j].z);

        /*
        printf("ID: %d | ISO: %s | Length: %f | Width: %f | Height: %f | Area: %f | MI: %s | MC: %f | MT: %f | CM: %s | CC: %f | CT: %f | OM: %s | OC: %f | OT: %f \n", containers[j].containerID, containers[j].isoCode, containers[j].length, containers[j].width, containers[j].height, containers[j].exposedArea, 
                                           containers[j].innerWallMaterial, containers[j].innerWallCondutivity, containers[j].innerWallThickness,
                                           containers[j].centerWallMaterial, containers[j].centerWallCondutivity, containers[j].centerWallThickness, 
                                           containers[j].outterWallMaterial, containers[j].outterWallCondutivity, containers[j].centerWallThickness);*/
    }


    return 0;
}