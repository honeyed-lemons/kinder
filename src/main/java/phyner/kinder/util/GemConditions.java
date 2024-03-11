package phyner.kinder.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GemConditions {
    public float tempMin;
    public float tempIdeal;
    public float tempMax;

    public float depthMin;
    public float depthMax;


    public HashMap<Item,GemColors> gems;

    public HashMap<String, Float> biome;

    /*
    white = 1
    yellow = 2
    blue = 3
    pink = 4
    yellow blue = 5
    yellow pink = 6
    pink blue = 7
    */

    public GemConditions(Float tempMin,Float tempIdeal,Float tempMax,Float depthMin,Float depthMax,HashMap<String, Float> biome,  HashMap<Item,GemColors> gems){
        this.tempMin = tempMin;
        this.tempIdeal = tempIdeal;
        this.tempMax = tempMax;
        this.depthMin = depthMin;
        this.depthMax = depthMax;
        this.biome = biome;
        this.gems = gems;
    }
}
