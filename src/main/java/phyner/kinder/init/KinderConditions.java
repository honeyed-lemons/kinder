package phyner.kinder.init;

import net.minecraft.entity.EntityType;
import phyner.kinder.entities.gems.QuartzEntity;
import phyner.kinder.entities.gems.RubyEntity;
import phyner.kinder.util.GemConditions;

import java.util.ArrayList;
import java.util.HashMap;

public class KinderConditions {
    public static ArrayList<GemConditions> conditions()
    {
        ArrayList<GemConditions> map = new ArrayList<>();
        map.add(RubyEntity.RubyConditions());
        map.add(QuartzEntity.QuartzConditions());
        return map;
    }
}

