package phyner.kinder.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.RubyEntity;

import java.util.ArrayList;
import java.util.List;

public class KinderGemEntities {
    public static List<EntityType<?>> GEMS = new ArrayList<>();

    public static final EntityType<RubyEntity> RUBY = FabricEntityTypeBuilder.create()
            .entityFactory(RubyEntity::new)
            .dimensions(EntityDimensions.fixed(0.8F,1.25F))
            .build();

    private static void register(String name, EntityType<?> type)
    {
        GEMS.add(type);
        Registry.register(Registries.ENTITY_TYPE, new Identifier(KinderMod.MOD_ID,name),type);
    }

    public static void registerEntities()
    {
        register("ruby",RUBY);
    }
    public static void registerAttributes()
    {
        FabricDefaultAttributeRegistry.register(RUBY, RubyEntity.createGemAttributes());
    }
}
