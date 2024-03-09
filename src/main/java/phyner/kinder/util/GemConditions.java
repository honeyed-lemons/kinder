package phyner.kinder.util;

import java.util.HashMap;

public class GemConditions {
    public float tempMin;
    public float tempIdeal;
    public float tempMax;

    public int essenceColor;
    public float depthMin;
    public float depthMax;

    public HashMap<String,Float> biome;

    /*
    white = 1
    yellow = 2
    blue = 3
    pink = 4
    yellow blue = 5
    yellow pink = 6
    pink blue = 7
    */

    public GemConditions(Float tempMin,Float tempIdeal, Float tempMax, Float depthMin, Float depthMax, int essenceColor, HashMap<String,Float> biome)
    {
        this.tempMin = tempMin;
        this.tempIdeal = tempIdeal;
        this.tempMax = tempMax;
        this.depthMin = depthMin;
        this.depthMax = depthMax;
        this.essenceColor = essenceColor;
        this.biome = biome;
    }
}
