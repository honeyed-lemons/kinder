package phyner.kinder.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.PearlEntity;
import phyner.kinder.entities.gems.QuartzEntity;
import phyner.kinder.entities.gems.RubyEntity;

import java.util.ArrayList;
import java.util.List;

public class KinderGemEntities {
    public static List<EntityType<?>> GEMS = new ArrayList<>();

    public static final EntityType<RubyEntity> RUBY = FabricEntityTypeBuilder.create()
            .entityFactory(RubyEntity::new)
            .dimensions(EntityDimensions.changing(0.6F,1.45F))
            .build();
    public static final EntityType<QuartzEntity> QUARTZ = FabricEntityTypeBuilder.create()
            .entityFactory(QuartzEntity::new)
            .dimensions(EntityDimensions.changing(0.6F,1.45F))
            .build();

    public static final EntityType<PearlEntity> PEARL = FabricEntityTypeBuilder.create()
            .entityFactory(PearlEntity::new)
            .dimensions(EntityDimensions.changing(0.6F,1.45F))
            .build();

    private static void register(String name, EntityType<?> type)
    {
        GEMS.add(type);
        Registry.register(Registries.ENTITY_TYPE, new Identifier(KinderMod.MOD_ID,name),type);
    }

    public static void registerEntities()
    {
        register("ruby",RUBY);
        register("quartz",QUARTZ);
        register("pearl",PEARL);

    }
    public static void registerAttributes()
    {
        FabricDefaultAttributeRegistry.register(RUBY, RubyEntity.createGemAttributes());
        FabricDefaultAttributeRegistry.register(PEARL, PearlEntity.createGemAttributes());
        FabricDefaultAttributeRegistry.register(QUARTZ, QuartzEntity.createGemAttributes());
    }
}
