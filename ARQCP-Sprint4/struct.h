typedef struct {
    int containerID;                    // 4 bytes
    char isoCode[5];                    // 5 bytes
    char x;                             // 1 byte
    char y;                             // 1 byte
    char z;                             // 1 byte

    float length;                       // 4 bytes
    float width;                        // 4 bytes
    float height;                       // 4 bytes

    float exposedArea;                  // 4 bytes

    char innerWallMaterial[16];         // 16 bytes
    float innerWallCondutivity;         // 4 bytes
    float innerWallThickness;           // 4 bytes

    char centerWallMaterial[16] ;       // 16 bytes
    float centerWallCondutivity;        // 4 bytes
    float centerWallThickness;          // 4 bytes
    
    char outerWallMaterial[16];         // 16 bytes
    float outerWallCondutivity;         // 4 bytes
    float outerWallThickness;           // 4 bytes

} container;                            // total: 100 bytes