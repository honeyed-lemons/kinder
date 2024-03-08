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
    white = 0
    yellow = 1
    blue = 2
    pink = 3
    yellow blue = 4
    yellow pink = 5
    pink blue = 6
    */
    public GemConditions(Float tempMin, Float tempMax, Float tempIdeal, Float depthMin, Float depthMax, int essenceColor)
    {
        this.tempMin = tempMin;
        this.tempIdeal = tempIdeal;
        this.tempMax = tempMax;
        this.depthMin = depthMin;
        this.depthMax = depthMax;
        this.essenceColor = essenceColor;
    }

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
