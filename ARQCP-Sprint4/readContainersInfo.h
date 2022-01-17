struct container;
container* getContainerGeneralInformation(  container *array,char *filename, int *size);
container* alloc_mem_for_arr(int size);
int getCargoManifestInformation( char *filename,  container *array, int n);
void addContainersWallInformation( container* array,int n,char *filename);