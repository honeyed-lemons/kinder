package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 8.0F);
    }

    public static AttributeSupplier.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(Attributes.MOVEMENT_SPEED, 0.6);
    }

    @Override
    public int maxHealth() {
        return KinderMod.config.rubyConfig.max_health;
    }

    @Override
    public int attackDamage() {
        return KinderMod.config.rubyConfig.attack_damage;
    }

    @Override
    @NotNull
    public SoundEvent gemInstrument() {
        return SoundEvents.NOTE_BLOCK_BASS.value();
    }

    @Override
    public boolean isSolider() {
        return true;
    }

    @Override
    public int hairVariantCount() {
        return 4;
    }

    @Override
    public int outfitVariantCount() {
        return 1;
    }

    @Override
    public boolean hasOutfitPlacementVariant() {
        return true;
    }

    @Override
    public int[] outfitPlacementVariants() {
        return new int[]{11, 12, 15};
    }

    @Override
    public int defaultOutfitColor() {
        return 14;
    }

    @Override
    public int defaultInsigniaColor() {
        return 14;
    }

    @Override
    public GemPlacements[] getPlacements() {
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

    @Override
    public int generateGemColorVariant() {
        return 0;
    }

    @Override
    public ItemStack gemItem() {
        return new ItemStack(KinderItems.RUBY_GEM);
    }

    public LivingEntity getOwner() {
        return super.getOwner();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public float damageModifier(Entity target) {
        if (target.isOnFire()) {
            return 1.25f;
        }
        return 1f;
    }

    @Override
    public void doEnemyThings(Entity target) {
        if ((0.045 * getPerfection()) - 0.035 > random.nextFloat()) {
            target.setSecondsOnFire(getPerfection() * 2);
        }
    }

    @Override
    public void containerChanged(Container sender) {

    }
}
