package phyner.kinder.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemFollowOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.entities.goals.GemWanderAroundGoal;
import phyner.kinder.util.ColorUtil;
import phyner.kinder.util.GemPlacements;
import phyner.kinder.util.PaletteType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractGemEntity extends TameableEntity implements GeoEntity, Tameable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Byte> TAMABLE_FLAGS = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> MOVEMENT_TYPE = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> REBEL = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> HAIR_COLOR = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SKIN_COLOR = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_COLOR_VARIANT = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HAIR_VARIANT = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> OUTFIT_VARIANT = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> INSIGNIA_VARIANT = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> OUTFIT_COLOR = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> INSIGNIA_COLOR = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_PLACEMENT = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_COLOR = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);


    private static UUID FOLLOW_ID;

    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public AbstractGemEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    public void initGoals() {
        //Looking around
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 2.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));
        //Movement Types and movement
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new GemWanderAroundGoal(this,this.getSpeed(), 0.0005f));
        this.goalSelector.add(5, new GemFollowOwnerGoal(this,this.getSpeed(), 2,48,true));
        //Combat
        this.goalSelector.add(1, new MeleeAttackGoal(this, this.getSpeed(), false));
        this.targetSelector.add(1, new GemAttackWithOwnerGoal(this,this.canFight()));
        this.targetSelector.add(1, new GemTrackOwnerAttackerGoal(this,this.canFight()));
        super.initGoals();
    }

    // Attributes

    public static DefaultAttributeContainer.Builder createDefaultGemAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }
    public double getSpeed()
    {
        return this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }
    public boolean canFight(){
        if (isSolider())
        {
            return true;
        }
        else return isRebel();
    }
    public abstract boolean isSolider();

    //Data
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(MOVEMENT_TYPE, (byte)0);
        this.dataTracker.startTracking(REBEL,false);
        this.dataTracker.startTracking(GEM_COLOR_VARIANT,0);
        this.dataTracker.startTracking(HAIR_VARIANT,0);
        this.dataTracker.startTracking(HAIR_COLOR, 0);
        this.dataTracker.startTracking(SKIN_COLOR, 0);
        this.dataTracker.startTracking(OUTFIT_COLOR, 0);
        this.dataTracker.startTracking(OUTFIT_VARIANT, 0);
        this.dataTracker.startTracking(INSIGNIA_COLOR, 0);
        this.dataTracker.startTracking(INSIGNIA_VARIANT, 0);
        this.dataTracker.startTracking(GEM_PLACEMENT, 0);
        this.dataTracker.startTracking(GEM_COLOR, 0);
    }
    // Colors
    public void setHairColor(int hairColor)
    {
        this.dataTracker.set(HAIR_COLOR,hairColor);
    }
    public int getHairColor()
    {
        return this.dataTracker.get(HAIR_COLOR);
    }
    public void setSkinColor(int skinColor)
    {
        this.dataTracker.set(SKIN_COLOR,skinColor);
    }
    public int getSkinColor()
    {
        return this.dataTracker.get(SKIN_COLOR);
    }

    public void setGemColor(int gemColor)
    {
        this.dataTracker.set(GEM_COLOR,gemColor);
    }
    public int getGemColor()
    {
        return this.dataTracker.get(GEM_COLOR);
    }
    private int getGemColorVariant(){
        return this.dataTracker.get(GEM_COLOR_VARIANT);
    }

    public abstract int hairVariantCount();
    public int generateHairVariant()
    {
        if (hairVariantCount()!=0) {
            return this.random.nextBetween(1,hairVariantCount());
        }
        else return 0;
    }
    public void setHairVariant(int hairVariant)
    {
        this.dataTracker.set(HAIR_VARIANT,hairVariant);
    }
    public int getHairVariant()
    {
        return this.dataTracker.get(HAIR_VARIANT);
    }
    public abstract int outfitVariantCount();
    public int generateOutfitVariant()
    {
        if (outfitVariantCount()!=0) {
            return this.random.nextBetween(1,outfitVariantCount());
        }
        else return 0;
    }
    public void setOutfitVariant(int outfitVariant)
    {
        this.dataTracker.set(OUTFIT_VARIANT,outfitVariant);
    }
    public int getOutfitVariant()
    {
        return this.dataTracker.get(OUTFIT_VARIANT);
    }
    public int generateInsigniaVariant()
    {
        return getOutfitVariant();
    }
    public void setInsigniaVariant(int insigniaVariant)
    {
        this.dataTracker.set(INSIGNIA_VARIANT,insigniaVariant);
    }
    public int getInsigniaVariant()
    {
        return this.dataTracker.get(INSIGNIA_VARIANT);
    }
    public int getInsigniaColor()
    {
        return this.dataTracker.get(INSIGNIA_COLOR);
    }
    public void setInsigniaColor(int insigniaColor)
    {
        this.dataTracker.set(INSIGNIA_COLOR,insigniaColor);
    }
    public int getOutfitColor()
    {
        return this.dataTracker.get(OUTFIT_COLOR);
    }
    public void setOutfitColor(int outfitColor)
    {
        this.dataTracker.set(OUTFIT_COLOR,outfitColor);
    }
    public abstract int defaultOutfitColor();
    public abstract int defaultInsigniaColor();
    public abstract GemPlacements[] getPlacements();
    public int generateGemPlacement(){
        return this.getPlacements()[this.random.nextInt(this.getPlacements().length)].id;
    }
    public void setGemPlacement(int gemPlacement)
    {
        this.dataTracker.set(GEM_PLACEMENT,gemPlacement);
    }
    public GemPlacements getGemPlacement()
    {
        return GemPlacements.getPlacement(this.dataTracker.get(GEM_PLACEMENT));
    }
    @Override
    public EntityData initialize(ServerWorldAccess world,LocalDifficulty difficulty,SpawnReason spawnReason,@Nullable EntityData entityData,@Nullable NbtCompound entityNbt){
        setHairVariant(generateHairVariant());
        setOutfitVariant(generateOutfitVariant());
        setInsigniaVariant(generateInsigniaVariant());
        setOutfitColor(defaultOutfitColor());
        setInsigniaColor(defaultInsigniaColor());
        setGemPlacement(generateGemPlacement());
        setHairColor(this.generatePaletteColor(PaletteType.HAIR));
        setSkinColor(this.generatePaletteColor(PaletteType.SKIN));
        setGemColor(this.generatePaletteColor(PaletteType.GEM));
        return entityData;
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if(!this.world.isClient()){
            ItemStack item = gemItem();
            NbtCompound nbt = new NbtCompound();
            nbt.putString("id",EntityType.getId(this.getType()).toString());
            this.writeNbt(nbt);
            item.getOrCreateNbt().put("gem",nbt);
            Objects.requireNonNull(this.dropStack(item)).setNeverDespawn();
        }
        super.onDeath(source);
    }
    abstract public ItemStack gemItem();

    public int generatePaletteColor(PaletteType type){
        String locString = type.type + "_palette";
        System.out.println("[DEBUG] " + locString);
        ArrayList<Integer> colors = new ArrayList<>();
        Identifier loc = new Identifier(KinderMod.MOD_ID + ":textures/entity/gems/" + this.getType().getUntranslatedName() + "/palettes/" + locString + ".png");
        BufferedImage palette = null;
        try {
            palette = ImageIO.read(MinecraftClient.getInstance().getResourceManager().getResource(loc).get().getInputStream());
            System.out.println("Palette Read!");
            for (int x = 0; x < palette.getWidth(); x++) {
                int color = palette.getRGB(x, this.getGemColorVariant());
                if ((color >> 24) == 0x00) {
                    continue;
                }
                colors.add(color);
            }
        } catch (IOException e) {
            e.printStackTrace();
            colors.add(0xFFFFFF);
        }
        return ColorUtil.lerpHex(colors);
    }


    /*
    Movement Type Values
    0 = Wander
    1 = Stay
    2 = Follow
    */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!player.world.isClient && !player.isSpectator()) {
            if (this.getOwner() == null) {
                KinderMod.LOGGER.info("There's no owner.");
            }
            if (player != this.getOwner()) {
                KinderMod.LOGGER.info(player.getName() + " is not the owner.");
            }
            if (player != getOwner() && getOwner() == null) {
                setOwner(player);
                player.sendMessage(Text.of(this.getName().getString() + " has been claimed."));
                return ActionResult.SUCCESS;
            }
            if (player == getOwner()) {
                if (player.isSneaking() && hand == Hand.MAIN_HAND && player.getStackInHand(Hand.MAIN_HAND) == ItemStack.EMPTY) {
                    Byte movementType = this.dataTracker.get(MOVEMENT_TYPE);
                    movementType = (byte) ((movementType + 1) % 3);
                    FOLLOW_ID = (movementType == 2) ? player.getUuid() : null;
                    switch (movementType) {
                        case 0 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.wander")));
                        case 1 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.stay")));
                        case 2 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.follow")));
                    }
                    this.dataTracker.set(MOVEMENT_TYPE,
                            movementType);
                    return ActionResult.SUCCESS;
                }
                if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof DyeItem dye) {
                    if (player.isSneaking())
                    {
                        this.setOutfitColor(dye.getColor().getId());
                    }
                    else
                    {
                        this.setInsigniaColor(dye.getColor().getId());
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.interactMob(player, hand);
    }

    public UUID getFollowId(){
        return FOLLOW_ID;
    }

    // Animation
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkLegsController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkArmsController(this));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putInt("MovementType", this.getMovementType());
        if (FOLLOW_ID != null)
        {
            nbt.putUuid("FollowID",FOLLOW_ID);
        }
        nbt.putByte("MovementType", this.dataTracker.get(MOVEMENT_TYPE));
        nbt.putBoolean("Rebel", this.dataTracker.get(REBEL));
        nbt.putInt("HairColor", this.dataTracker.get(HAIR_COLOR));
        nbt.putInt("SkinColor", this.dataTracker.get(SKIN_COLOR));
        nbt.putInt("GemColorVariant", this.dataTracker.get(GEM_COLOR_VARIANT));
        nbt.putInt("HairVariant", this.dataTracker.get(HAIR_VARIANT));
        nbt.putInt("OutfitColor", this.dataTracker.get(OUTFIT_COLOR));
        nbt.putInt("InsigniaColor", this.dataTracker.get(INSIGNIA_COLOR));
        nbt.putInt("OutfitVariant", this.dataTracker.get(OUTFIT_VARIANT));
        nbt.putInt("InsigniaVariant", this.dataTracker.get(INSIGNIA_VARIANT));
        nbt.putInt("GemPlacement", this.dataTracker.get(GEM_PLACEMENT));
        nbt.putInt("GemColor", this.dataTracker.get(GEM_COLOR));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        UUID uuid;
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("Owner")) {
            uuid = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uuid != null) {
            try {
                this.setOwnerUuid(uuid);
                this.setTamed(true);
            } catch (Throwable throwable) {
                this.setTamed(false);
            }
        }
        if (nbt.containsUuid("FollowID")) {
            FOLLOW_ID = nbt.getUuid("FollowID");
        }
        this.dataTracker.set(MOVEMENT_TYPE,nbt.getByte("MovementType"));
        this.dataTracker.set(REBEL,nbt.getBoolean("Rebel"));
        this.dataTracker.set(HAIR_COLOR,nbt.getInt("HairColor"));
        this.dataTracker.set(SKIN_COLOR,nbt.getInt("SkinColor"));
        this.dataTracker.set(GEM_COLOR_VARIANT,nbt.getInt("GemColorVariant"));
        this.dataTracker.set(HAIR_VARIANT,nbt.getInt("HairVariant"));
        this.dataTracker.set(OUTFIT_COLOR,nbt.getInt("OutfitColor"));
        this.dataTracker.set(INSIGNIA_COLOR,nbt.getInt("InsigniaColor"));
        this.dataTracker.set(OUTFIT_VARIANT,nbt.getInt("OutfitVariant"));
        this.dataTracker.set(INSIGNIA_VARIANT,nbt.getInt("InsigniaVariant"));
        this.dataTracker.set(GEM_PLACEMENT,nbt.getInt("GemPlacement"));
        this.dataTracker.set(GEM_COLOR,nbt.getInt("GemColor"));
    }

    public byte getMovementType()
    {
        return this.dataTracker.get(MOVEMENT_TYPE);
    }

    public boolean isRebel()
    {
        return dataTracker.get(REBEL);
    }

    /* Sounds */
    @NotNull
    public abstract SoundEvent gemInstrument();

    @Override
    public void playAmbientSound() {
        this.playSound(gemInstrument(), 1, this.getSoundPitch());
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof Tameable)
        {
            return ((Tameable) target).getOwner() != owner;
        }
        else return !(target instanceof CreeperEntity);
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world,PassiveEntity entity){
        return null;
    }
    @Override
    public EntityView method_48926(){
        return this.getEntityWorld();
    }
}

