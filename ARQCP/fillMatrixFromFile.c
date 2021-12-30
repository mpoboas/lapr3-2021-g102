#include <stdio.h>
#include <stdlib.h>
void fillMatrixFromFile(int *(*matrix), char *fileName){
    FILE *fp;
    fp = fopen(fileName, "r");
    char  fileContets[50];//array of chars to store the file contents
    char *ptrTemp = NULL;
    char *pointFileContents;
    int i;
    for(i = 0 ; *(fileContets + i) != '\0';i++){
      *(pointFileContents +i) = fgetc(fp);
      if(i == sizeof(fileContets) -1){
          ptrTemp = (char *) realloc(fileContets, i+5);
          if(ptrTemp != NULL){
              pointFileContents = ptrTemp;
              ptrTemp = NULL;
          }
      }
      
   }
}