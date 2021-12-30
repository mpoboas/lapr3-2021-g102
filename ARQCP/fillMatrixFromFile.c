void fillMatrixFromFile(int *matrix, char *fileName){
    FILE *fp;
    fp = fopen(*filename, "r");
    char [50] fileContets ;
    char *ptrTemp = NULL;
    int i;
    for(i = 0 ; *(fileContets + i) != '\0';i++){
      *(fileContets +i) = fgetc(fp);
      if(i == sizeof(fileContets) -1){
          ptrTemp = (char *) realloc(fileContets, i+5);
          if(ptrTemp != NULL){
              fileContets = ptrTemp;
              ptrTemp = NULL;
          }
      }
   }
}