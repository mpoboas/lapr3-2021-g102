#include <stdio.h>
#include "get_needed_energy_container.h"
//#include "is_container_refrigerated.h"

static float get_wall_thermal_resistance(float wallThickness, float thermalConductivity, float exposedArea)
{
    if (thermalConductivity > 0 && exposedArea > 0)
    {
        return (wallThickness / thermalConductivity) / exposedArea; // Calculates the thermal resistance of a wall
    }
    
    return 0;
}

static float get_exposed_area(float length, float width, float height)
{
    float supInfArea = length * width * 2; // Calculates the superior and inferior surfaces´ area
    float latArea = length * height * 2; // Calculates the lateral surfaces´ area
    float frontBackArea = width * height * 2; // Calculates the frontal and back surfaces´ area
    return supInfArea + latArea + frontBackArea; // Returns the total exposed area
}

float get_needed_energy_container(float hours, float outerTemperature, container *contVec, int n, char x, char y, char z)
{
    //if (is_container_refrigerated(x, y, z) == 0 || hours <= 0) return 0;
    
    for (int i = 0; i < n; i++)
    {
        if ((contVec + i)->x == x && (contVec + i)->y == y && (contVec + i)->z == z)
        {
            float exposedArea = get_exposed_area((contVec + i)->length, (contVec + i)->width, (contVec + i)->height); // Gets the total exposed area
            // There are three wall subsections in a container´s wall: outer, central and inner
            float totalResistance = get_wall_thermal_resistance((contVec + i)->innerWallThickness, (contVec + i)->innerWallCondutivity, exposedArea);
            totalResistance += get_wall_thermal_resistance((contVec + i)->centerWallThickness, (contVec + i)->centerWallCondutivity, exposedArea);
            totalResistance += get_wall_thermal_resistance((contVec + i)->outerWallThickness, (contVec + i)->outerWallCondutivity, exposedArea);
            if (totalResistance == 0) return 0; // If it happens to be 0, the heatflow can´t be calculated

            char innerTemperature = (contVec + i)->isoCode[3] - '0'; // Gets the container´s desired inner temperature, through its isoCode pattern (ex: '7' - '0' = 55 - 48 = 7)
            float heatFlow = (outerTemperature - innerTemperature) / totalResistance; // Calculates the heatflow
            return heatFlow * (hours * 60 * 60); // Return the total needed energy
        }
    }

    return 0;
}