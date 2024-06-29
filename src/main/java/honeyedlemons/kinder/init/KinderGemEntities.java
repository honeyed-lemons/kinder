package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.client.render.gems.PearlEntityRenderer;
import honeyedlemons.kinder.client.render.gems.QuartzEntityRenderer;
import honeyedlemons.kinder.client.render.gems.RubyEntityRenderer;
import honeyedlemons.kinder.client.render.gems.SapphireEntityRenderer;
import honeyedlemons.kinder.entities.gems.PearlEntity;
import honeyedlemons.kinder.entities.gems.QuartzEntity;
import honeyedlemons.kinder.entities.gems.RubyEntity;
import honeyedlemons.kinder.entities.gems.SapphireEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import java.util.ArrayList;
import java.util.List;

public class KinderGemEntities {
    public static final EntityType<RubyEntity> RUBY = FabricEntityTypeBuilder.create().entityFactory(RubyEntity::new).dimensions(EntityDimensions.scalable(0.6F, 1.2F)).build();
    public static final EntityType<QuartzEntity> QUARTZ = FabricEntityTypeBuilder.create().entityFactory(QuartzEntity::new).dimensions(EntityDimensions.scalable(0.8F, 2F)).build();
    public static final EntityType<PearlEntity> PEARL = FabricEntityTypeBuilder.create().entityFactory(PearlEntity::new).dimensions(EntityDimensions.scalable(0.6F, 1.75F)).build();

    public static final EntityType<SapphireEntity> SAPPHIRE = FabricEntityTypeBuilder.create().entityFactory(SapphireEntity::new).dimensions(EntityDimensions.scalable(0.6F, 1.0F)).build();

    public static List<EntityType<?>> GEMS = new ArrayList<>();

    private static void register(String name, EntityType<?> type) {
        GEMS.add(type);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(KinderMod.MOD_ID, name), type);
    }

    public static void registerEntities() {
        register("ruby", RUBY);
        register("quartz", QUARTZ);
        register("pearl", PEARL);
        register("sapphire", SAPPHIRE);

    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(RUBY, RubyEntity.createGemAttributes());
        FabricDefaultAttributeRegistry.register(PEARL, PearlEntity.createGemAttributes());
        FabricDefaultAttributeRegistry.register(QUARTZ, QuartzEntity.createGemAttributes());
        FabricDefaultAttributeRegistry.register(SAPPHIRE, SapphireEntity.createGemAttributes());
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(KinderGemEntities.RUBY, RubyEntityRenderer::new);
        EntityRendererRegistry.register(KinderGemEntities.QUARTZ, QuartzEntityRenderer::new);
        EntityRendererRegistry.register(KinderGemEntities.PEARL, PearlEntityRenderer::new);
        EntityRendererRegistry.register(KinderGemEntities.SAPPHIRE, SapphireEntityRenderer::new);
    }
}
