package phyner.kinder.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import phyner.kinder.entities.gems.QuartzEntity;
import phyner.kinder.entities.gems.RubyEntity;
import phyner.kinder.util.GemConditions;

import java.util.HashMap;

public class KinderConditions {
    public static HashMap<EntityType, GemConditions> GemConditions()
    {
        HashMap<EntityType,GemConditions> hash = new HashMap<>();
        hash.put(KinderGemEntities.RUBY,RubyEntity.RubyConditions());
        hash.put(KinderGemEntities.QUARTZ,QuartzEntity.QuartzConditions());
        return hash;
    }
}

