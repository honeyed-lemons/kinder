package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.init.KinderItems;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.GemConditions;
import phyner.kinder.util.GemPlacements;

import java.util.HashMap;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes(){
        return createDefaultGemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.60)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,3.5);
    }
    public static GemConditions RubyConditions()
    {
        float tempMin = 0.5f;
        float tempIdeal = 2.0f;
        float tempMax = 2.0f;
        float depthMax = -255;
        float depthMin = 80;
        HashMap<String,Float> biomes = new HashMap<>();
        biomes.put("minecraft:nether_wastes",1f);
        biomes.put("minecraft:warped_forest",1f);
        biomes.put("minecraft:crimson_forest",1f);
        biomes.put("minecraft:basalt_deltas",1f);
        biomes.put("minecraft:soul_sand_valley",1f);
        HashMap<Item, GemColors> gem = new HashMap<>();
        gem.put(KinderItems.RUBY_GEM,GemColors.RED);
        return new GemConditions(tempMin,tempIdeal,tempMax,depthMin,depthMax,biomes,gem);
    }
    @Override
    @NotNull
    public SoundEvent gemInstrument(){
        return SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
    }

    @Override
    public boolean isSolider(){
        return true;
    }

    @Override
    public int hairVariantCount(){
        return 4;
    }

    @Override
    public int outfitVariantCount(){
        return 1;
    }

    @Override
    public boolean hasOutfitPlacementVariant(){
        return true;
    }
    @Override
    public int[] outfitPlacementVariants(){
        return new int[]{
                11,12,15
        };
    }
    @Override
    public int defaultOutfitColor(){
        return 14;
    }

    @Override
    public int defaultInsigniaColor(){
        return 14;
    }

    @Override
    public GemPlacements[] getPlacements(){
        return new GemPlacements[]{
                GemPlacements.FOREHEAD, GemPlacements.CHEST, GemPlacements.BELLY, GemPlacements.BACK_OF_HEAD, GemPlacements.LEFT_EYE,
                GemPlacements.RIGHT_EYE,GemPlacements.LEFT_HAND,GemPlacements.RIGHT_HAND,GemPlacements.LEFT_KNEE,GemPlacements.RIGHT_KNEE,
                GemPlacements.RIGHT_SHOULDER,GemPlacements.RIGHT_SHOULDER,GemPlacements.BACK, GemPlacements.NOSE
        };
    }
    @Override
    public int generateGemColorVariant(){
        return 0;
    }

    @Override
    public ItemStack gemItem() {
        return new ItemStack(KinderItems.RUBY_GEM);
    }

    public LivingEntity getOwner(){
        return super.getOwner();
    }

    @Override
    public boolean isFireImmune()
    {
        return true;
    }

    @Override
    public void onInventoryChanged(Inventory sender){

    }
}
