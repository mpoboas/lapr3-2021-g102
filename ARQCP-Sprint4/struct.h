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

} container;