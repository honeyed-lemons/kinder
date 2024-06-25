package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PearlEntity extends AbstractVaryingGemEntity {
    public PearlEntity (EntityType<? extends TameableEntity> entityType, World world){
        super (entityType, world);
    }
    private static final TrackedData<Integer> HAIR_EXTRA_VARIANT = DataTracker.registerData (PearlEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes (){
        return createDefaultGemAttributes ().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.625);
    }
    public EntityData initialize (ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt){
        this.setHairExtraVariant(generateHairExtraVariant());
        return super.initialize (world, difficulty, spawnReason, entityData, entityNbt);
    }
    @Override public int maxHealth (){
        return KinderMod.config.pearlConfig.max_health;
    }

    @Override public int attackDamage (){
        return KinderMod.config.pearlConfig.attack_damage;
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new EscapeDangerGoal(this, getSpeed()));
    }

    @Override public void interactGem (PlayerEntity player){
        ItemStack stack = player.getStackInHand (Hand.MAIN_HAND);
        if (stack == ItemStack.EMPTY)
        {
            player.openHandledScreen (new GemScreenHandlerFactory ());
        }
    }
    @Override public boolean isSolider (){
        return false;
    }

    @Override public int hairVariantCount (){
        return 10;
    }

    public int hairExtraVariantCount (){
        return 5;
    }

    public void initDataTracker (){
        super.initDataTracker ();
        this.dataTracker.startTracking (HAIR_EXTRA_VARIANT, 0);
    }
    public void writeCustomDataToNbt (NbtCompound nbt){
        nbt.putInt ("HairExtraVariant", this.dataTracker.get (HAIR_EXTRA_VARIANT));
        super.writeCustomDataToNbt (nbt);
    }

    public void readCustomDataFromNbt (NbtCompound nbt){
        this.dataTracker.set (HAIR_EXTRA_VARIANT, nbt.getInt ("HairExtraVariant"));
        super.readCustomDataFromNbt (nbt);
    }

    @Override public int outfitVariantCount (){
        return 5;
    }

    public int insigniaVariantCount (){
        return 6;
    }
    public int getHairExtraVariant (){
        return this.dataTracker.get(HAIR_EXTRA_VARIANT);
    }
    public void setHairExtraVariant (int hairVariant) {
        this.dataTracker.set(HAIR_EXTRA_VARIANT,hairVariant);
    }
    @Override public int generateInsigniaVariant (){
        if (insigniaVariantCount () != 0) {
            return this.random.nextBetween (1, insigniaVariantCount());
        } else return 0;
    }

    public int generateHairExtraVariant()
    {
        return this.random.nextInt(hairExtraVariantCount());
    }
    @Override public boolean hasOutfitPlacementVariant (){
        return false;
    }

    @Override public int defaultOutfitColor (){
        return -1;
    }

    @Override public int defaultInsigniaColor (){
        return -1;
    }

    @Override public GemPlacements[] getPlacements (){
        return new GemPlacements[]{
                GemPlacements.CHEST,
                GemPlacements.NOSE,
                GemPlacements.BACK,
                GemPlacements.BELLY,
                GemPlacements.FOREHEAD,
                GemPlacements.RIGHT_EYE,
                GemPlacements.LEFT_EYE,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.LEFT_SHOULDER,
                GemPlacements.LEFT_CHEEK,
                GemPlacements.RIGHT_CHEEK,
                GemPlacements.RIGHT_HAND,
                GemPlacements.LEFT_HAND,
                GemPlacements.RIGHT_KNEE,
                GemPlacements.LEFT_KNEE};
    }

    @Override public ItemStack gemItem (){
        return switch (getGemColorVariant ()) {
            case 1 -> KinderItems.PEARL_GEM_1.getDefaultStack ();
            case 2 -> KinderItems.PEARL_GEM_2.getDefaultStack ();
            case 3 -> KinderItems.PEARL_GEM_3.getDefaultStack ();
            case 4 -> KinderItems.PEARL_GEM_4.getDefaultStack ();
            case 5 -> KinderItems.PEARL_GEM_5.getDefaultStack ();
            case 6 -> KinderItems.PEARL_GEM_6.getDefaultStack ();
            case 7 -> KinderItems.PEARL_GEM_7.getDefaultStack ();
            case 8 -> KinderItems.PEARL_GEM_8.getDefaultStack ();
            case 9 -> KinderItems.PEARL_GEM_9.getDefaultStack ();
            case 10 -> KinderItems.PEARL_GEM_10.getDefaultStack ();
            case 11 -> KinderItems.PEARL_GEM_11.getDefaultStack ();
            case 12 -> KinderItems.PEARL_GEM_12.getDefaultStack ();
            case 13 -> KinderItems.PEARL_GEM_13.getDefaultStack ();
            case 14 -> KinderItems.PEARL_GEM_14.getDefaultStack ();
            case 15 -> KinderItems.PEARL_GEM_15.getDefaultStack ();
            default -> KinderItems.PEARL_GEM_0.getDefaultStack ();
        };
    }

    @Override public @NotNull SoundEvent gemInstrument (){
        return SoundEvents.BLOCK_NOTE_BLOCK_HARP.value ();
    }

    @Override public boolean UsesUniqueNames (){
        return true;
    }

    @Override public void onInventoryChanged (Inventory sender){
    }
    @Override
    public int[] neglectedColors() {
        return null;
    }

}
