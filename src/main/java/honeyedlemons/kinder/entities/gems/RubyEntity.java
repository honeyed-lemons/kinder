package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity (EntityType<? extends AbstractGemEntity> entityType, World world){
        super (entityType, world);
        this.setPathfindingPenalty (PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty (PathNodeType.LAVA, 0.0F);
        this.setPathfindingPenalty (PathNodeType.DAMAGE_FIRE, 0.0F);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes (){
        return createDefaultGemAttributes ().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }
    @Override public int maxHealth (){
        return 30;
    }

    @Override public int attackDamage (){
        return 3;
    }

    @Override @NotNull public SoundEvent gemInstrument (){
        return SoundEvents.BLOCK_NOTE_BLOCK_BASS.value ();
    }

    @Override public boolean isSolider (){
        return true;
    }

    @Override public int hairVariantCount (){
        return 4;
    }

    @Override public int outfitVariantCount (){
        return 1;
    }

    @Override public boolean hasOutfitPlacementVariant (){
        return true;
    }

    @Override public int[] outfitPlacementVariants (){
        return new int[]{11, 12, 15};
    }

    @Override public int defaultOutfitColor (){
        return 14;
    }

    @Override public int defaultInsigniaColor (){
        return 14;
    }

    @Override public GemPlacements[] getPlacements (){
        return new GemPlacements[]{
                GemPlacements.FOREHEAD,
                GemPlacements.CHEST,
                GemPlacements.BELLY,
                GemPlacements.BACK_OF_HEAD,
                GemPlacements.LEFT_EYE,
                GemPlacements.RIGHT_EYE,
                GemPlacements.LEFT_HAND,
                GemPlacements.RIGHT_HAND,
                GemPlacements.LEFT_KNEE,
                GemPlacements.RIGHT_KNEE,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.BACK,
                GemPlacements.NOSE};
    }

    @Override public int generateGemColorVariant (){
        return 0;
    }

    @Override public ItemStack gemItem (){
        return new ItemStack (KinderItems.RUBY_GEM);
    }

    public LivingEntity getOwner (){
        return super.getOwner ();
    }

    @Override public boolean isFireImmune (){
        return true;
    }

    @Override public void onInventoryChanged (Inventory sender){

    }
}
