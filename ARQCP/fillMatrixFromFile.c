#include <stdio.h>
#include <stdlib.h>
void fillMatrixFromFile(int *(*matrix), char *fileName){
    FILE *fp;
    fp = fopen(fileName, "r");
    char *ptrTemp = NULL;
    char *pointFileContents = NULL;
    int lineCount = 0;
    pointFileContents = (char *)malloc(50* sizeof(char));
    int i;
    for(i = 0 ; !feof(fp);i++){
      fscanf(fp,"%c",(pointFileContents +i));
      if(*(pointFileContents +i) == '\n'){
          lineCount = lineCount +1;
      }
      if(i == sizeof(pointFileContents) -1){
          ptrTemp = (char *) realloc(pointFileContents, (i+5) + sizeof(char));
          if(ptrTemp != NULL){
              pointFileContents = ptrTemp;
              ptrTemp = NULL;
          }
      }
   }
    int charCount = 0;
    int k = 0;
    int j = 0;
    while(j < lineCount){
        k = 0;
        while(pointFileContents[charCount] != '\n'){
          if(pointFileContents[charCount] != ','){
                *(*(matrix+j)+k) = (*(*(matrix+j)+k) * 10) + (pointFileContents[charCount]-48);
            }else{
                k = k +1;
            }
            charCount = charCount +1;
        }
        charCount = charCount +1;
        j = j +1;
    }
}
void fill3DMatrix(int ***matrix, int **fileContents, int nMaxContainers){
    int j = 1;
    for(int i = 0; i < nMaxContainers; i++){
        *(*(*(matrix+*(*(fileContents+i)+j))+*(*(fileContents+i)+j+1))+ *(*(fileContents+i)+j+2)) = *(*(fileContents+i));
    }
}