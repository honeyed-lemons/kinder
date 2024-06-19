package phyner.kinder.entities;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
import phyner.kinder.client.render.screens.handlers.PearlScreenHandler;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemFollowOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.entities.goals.GemWanderAroundGoal;
import phyner.kinder.init.KinderItems;
import phyner.kinder.items.DestabItem;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.GemPlacements;
import phyner.kinder.util.InventoryNbtUtil;
import phyner.kinder.util.PaletteType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
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

public abstract class AbstractGemEntity extends TameableEntity implements GeoEntity, InventoryChangedListener, Tameable {
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Byte> TAMABLE_FLAGS = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> MOVEMENT_TYPE = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> REBEL = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> REBEL_CHANCE = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> REBEL_BASE_CHANCE = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> REBEL_TICKS = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> PERFECTION = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HAIR_COLOR = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SKIN_COLOR = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_COLOR_VARIANT = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HAIR_VARIANT = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> OUTFIT_VARIANT = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> INSIGNIA_VARIANT = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> OUTFIT_COLOR = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> INSIGNIA_COLOR = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_PLACEMENT = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GEM_COLOR = DataTracker.registerData (AbstractGemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static UUID FOLLOW_ID;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache (this);
    public int initalGemColorVariant = 0;
    public boolean setGemVariantOnInitialSpawn = true;
    private SimpleInventory inventory;

    @SuppressWarnings("deprecation")
    public AbstractGemEntity (EntityType<? extends TameableEntity> entityType, World world){
        super (entityType, world);
        this.reinitDimensions();
        this.updateInventory ();
    }

    public static DefaultAttributeContainer.Builder createDefaultGemAttributes (){
        return LivingEntity.createLivingAttributes ().add (EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add (EntityAttributes.GENERIC_FOLLOW_RANGE, 32).add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    // Attributes

    public void initGoals (){
        //Looking around
        this.goalSelector.add (10, new LookAtEntityGoal (this, PlayerEntity.class, 2.0f));
        this.goalSelector.add (10, new LookAroundGoal (this));
        //Movement Types and movement
        this.goalSelector.add (1, new SwimGoal (this));
        this.goalSelector.add (5, new GemWanderAroundGoal (this, this.getSpeed (), 0.0005f));
        this.goalSelector.add (5, new GemFollowOwnerGoal (this, this.getSpeed (), 2, 48, true));
        //Combat
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false, (entity) -> this.isRebel()));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MobEntity.class, 1, false, false, (entity) -> this.canFight() && (entity.getGroup().equals(EntityGroup.UNDEAD) || entity instanceof SpiderEntity  || entity.getGroup().equals(EntityGroup.ILLAGER))));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, AbstractGemEntity.class, true, (entity) -> !entity.getDataTracker().get(REBEL) && this.isRebel()));
        this.goalSelector.add (1, new MeleeAttackGoal (this, this.getSpeed (), false));
        this.targetSelector.add (1, new GemAttackWithOwnerGoal (this, this.canFight ()));
        this.targetSelector.add (1, new GemTrackOwnerAttackerGoal (this, this.canFight ()));
        super.initGoals ();
    }

    public double getSpeed (){
        return this.getAttributeValue (EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }
    public boolean canFight (){
        if (isSolider ()) {
            return true;
        }
        else return isRebel ();
    }

    public abstract boolean isSolider ();

    //Data
    public void initDataTracker (){
        super.initDataTracker ();
        this.dataTracker.startTracking (TAMABLE_FLAGS, (byte) 0);
        this.dataTracker.startTracking (OWNER_UUID, Optional.empty ());
        this.dataTracker.startTracking (MOVEMENT_TYPE, (byte) 0);
        this.dataTracker.startTracking (REBEL, false);
        this.dataTracker.startTracking (REBEL_TICKS, 24000);
        this.dataTracker.startTracking (REBEL_CHANCE, 0f);
        this.dataTracker.startTracking (REBEL_BASE_CHANCE, 5);
        this.dataTracker.startTracking (GEM_COLOR_VARIANT, 0);
        this.dataTracker.startTracking (HAIR_VARIANT, 0);
        this.dataTracker.startTracking (HAIR_COLOR, 0);
        this.dataTracker.startTracking (SKIN_COLOR, 0);
        this.dataTracker.startTracking (OUTFIT_COLOR, 0);
        this.dataTracker.startTracking (OUTFIT_VARIANT, 0);
        this.dataTracker.startTracking (INSIGNIA_COLOR, 0);
        this.dataTracker.startTracking (INSIGNIA_VARIANT, 0);
        this.dataTracker.startTracking (GEM_PLACEMENT, 0);
        this.dataTracker.startTracking (GEM_COLOR, 0);
        this.dataTracker.startTracking (PERFECTION, 6);
    }
    @Override
    public void writeCustomDataToNbt (NbtCompound nbt){
        super.writeCustomDataToNbt (nbt);
        if (this.getOwnerUuid () != null) {
            nbt.putUuid ("Owner", this.getOwnerUuid ());
        }
        nbt.putInt ("MovementType", this.getMovementType ());
        if (FOLLOW_ID != null) {
            nbt.putUuid ("FollowID", FOLLOW_ID);
        }
        nbt.putByte ("MovementType", this.dataTracker.get (MOVEMENT_TYPE));
        nbt.putBoolean ("Rebel", this.dataTracker.get (REBEL));
        nbt.putFloat ("RebelChance", this.dataTracker.get (REBEL_CHANCE));
        nbt.putInt ("RebelBaseChance", this.dataTracker.get (REBEL_BASE_CHANCE));
        nbt.putInt ("RebelTicks", this.dataTracker.get (REBEL_TICKS));
        nbt.putInt ("Perfection", this.dataTracker.get (PERFECTION));
        nbt.putInt ("HairColor", this.dataTracker.get (HAIR_COLOR));
        nbt.putInt ("SkinColor", this.dataTracker.get (SKIN_COLOR));
        nbt.putInt ("GemColorVariant", this.dataTracker.get (GEM_COLOR_VARIANT));
        nbt.putInt ("HairVariant", this.dataTracker.get (HAIR_VARIANT));
        nbt.putInt ("OutfitColor", this.dataTracker.get (OUTFIT_COLOR));
        nbt.putInt ("InsigniaColor", this.dataTracker.get (INSIGNIA_COLOR));
        nbt.putInt ("OutfitVariant", this.dataTracker.get (OUTFIT_VARIANT));
        nbt.putInt ("InsigniaVariant", this.dataTracker.get (INSIGNIA_VARIANT));
        nbt.putInt ("GemPlacement", this.dataTracker.get (GEM_PLACEMENT));
        nbt.putInt ("GemColor", this.dataTracker.get (GEM_COLOR));
        InventoryNbtUtil.writeInventoryNbt (nbt, "inventory", this.inventory, this.inventory.size ());
    }
    @Override
    public void readCustomDataFromNbt (NbtCompound nbt){
        UUID uuid;
        this.setPerfectionThings(nbt.getInt("Perfection"));
        super.readCustomDataFromNbt (nbt);
        if (nbt.containsUuid ("Owner")) {
            uuid = nbt.getUuid ("Owner");
        } else {
            String string = nbt.getString ("Owner");
            uuid = ServerConfigHandler.getPlayerUuidByName (this.getServer (), string);
        }
        if (uuid != null) {
            try {
                this.setOwnerUuid (uuid);
                this.setTamed (true);
            } catch (Throwable throwable) {
                this.setTamed (false);
            }
        }
        if (nbt.containsUuid ("FollowID")) {
            FOLLOW_ID = nbt.getUuid ("FollowID");
        }this.dataTracker.set (MOVEMENT_TYPE, nbt.getByte ("MovementType"));
        this.dataTracker.set (REBEL, nbt.getBoolean ("Rebel"));
        this.dataTracker.set (REBEL_CHANCE, nbt.getFloat ("RebelChance"));
        this.dataTracker.set (REBEL_TICKS, nbt.getInt ("RebelTicks"));
        this.dataTracker.set (REBEL_BASE_CHANCE, nbt.getInt ("RebelBaseChance"));
        this.dataTracker.set (PERFECTION, nbt.getInt ("Perfection"));
        this.dataTracker.set (HAIR_COLOR, nbt.getInt ("HairColor"));
        this.dataTracker.set (SKIN_COLOR, nbt.getInt ("SkinColor"));
        this.dataTracker.set (GEM_COLOR_VARIANT, nbt.getInt ("GemColorVariant"));
        this.dataTracker.set (HAIR_VARIANT, nbt.getInt ("HairVariant"));
        this.dataTracker.set (OUTFIT_COLOR, nbt.getInt ("OutfitColor"));
        this.dataTracker.set (INSIGNIA_COLOR, nbt.getInt ("InsigniaColor"));
        this.dataTracker.set (OUTFIT_VARIANT, nbt.getInt ("OutfitVariant"));
        this.dataTracker.set (INSIGNIA_VARIANT, nbt.getInt ("InsigniaVariant"));
        this.dataTracker.set (GEM_PLACEMENT, nbt.getInt ("GemPlacement"));
        this.dataTracker.set (GEM_COLOR, nbt.getInt ("GemColor"));
        InventoryNbtUtil.readInventoryNbt (nbt, "Inventory", this.inventory);
    }

    public void setRebelBaseChance(int i)
    {
        this.dataTracker.set(REBEL_BASE_CHANCE,i);
    }
    public int getRebelBaseChance()
    {
        return this.dataTracker.get(REBEL_BASE_CHANCE);
    }
    public void addRebelChance(float f)
    {
        this.dataTracker.set(REBEL_CHANCE,getRebelChance()+f);
    }
    public float getRebelChance()
    {
        return this.dataTracker.get(REBEL_CHANCE);
    }
    public void setRebel(boolean b)
    {
        this.dataTracker.set(REBEL,b);
        this.dataTracker.set(MOVEMENT_TYPE,(byte)0);
        this.setOwnerUuid(null);
        this.setTamed(false);
    }

    @Override
    public void tick (){
        super.tick();
        if (getRebelTicks() <= 0) {
            checkRebel();
            setRebelTicks(24000);
        }
        else{
            setRebelTicks(getRebelTicks() - 1);
        }
    }

    public void checkRebel (){
        if (getRebelChance() > getRebelBaseChance())
        {
            if ((random.nextFloat() * getRebelChance()) > (random.nextFloat() * 10))
            {
                this.setRebel(true);
            }
        }
    }

    public void setRebelTicks(int i)
    {
        this.dataTracker.set(REBEL_TICKS,i);
    }
    public int getRebelTicks()
    {
        return this.dataTracker.get(REBEL_TICKS);
    }
    public int getHairColor (){
        return this.dataTracker.get (HAIR_COLOR);
    }

    // Colors
    public void setHairColor (int hairColor){
        this.dataTracker.set (HAIR_COLOR, hairColor);
    }

    public int getSkinColor (){
        return this.dataTracker.get (SKIN_COLOR);
    }

    public void setSkinColor (int skinColor){
        this.dataTracker.set (SKIN_COLOR, skinColor);
    }

    public int getGemColor (){
        return this.dataTracker.get (GEM_COLOR);
    }

    public void setGemColor (int gemColor){
        this.dataTracker.set (GEM_COLOR, gemColor);
    }

    public int getGemColorVariant (){
        return this.dataTracker.get (GEM_COLOR_VARIANT);
    }

    public void setGemColorVariant (int colorVariant){
        this.dataTracker.set (GEM_COLOR_VARIANT, colorVariant);
    }

    public int getPerfection (){
        return this.dataTracker.get(PERFECTION);
    }

    public void setPerfection (int perfection){
        this.dataTracker.set (PERFECTION, perfection);
    }

    public abstract int hairVariantCount ();

    public int generateHairVariant (){
        if (hairVariantCount () != 0) {
            return this.random.nextBetween (1, hairVariantCount ());
        } else return 0;
    }

    public int getHairVariant (){
        return this.dataTracker.get (HAIR_VARIANT);
    }

    public void setHairVariant (int hairVariant){
        this.dataTracker.set (HAIR_VARIANT, hairVariant);
    }

    public abstract int outfitVariantCount ();

    public int generateOutfitVariant (){
        if (outfitVariantCount () != 0) {
            return this.random.nextBetween (1, outfitVariantCount ());
        } else return 0;
    }

    public abstract boolean hasOutfitPlacementVariant ();

    public int[] outfitPlacementVariants (){
        return new int[]{};
    }

    public int getOutfitVariant (){
        return this.dataTracker.get (OUTFIT_VARIANT);
    }

    public void setOutfitVariant (int outfitVariant){
        this.dataTracker.set (OUTFIT_VARIANT, outfitVariant);
    }

    public int generateInsigniaVariant (){
        return getOutfitVariant ();
    }

    public int getInsigniaVariant (){
        return this.dataTracker.get (INSIGNIA_VARIANT);
    }

    public void setInsigniaVariant (int insigniaVariant){
        this.dataTracker.set (INSIGNIA_VARIANT, insigniaVariant);
    }

    public int getInsigniaColor (){
        return this.dataTracker.get (INSIGNIA_COLOR);
    }

    public void setInsigniaColor (int insigniaColor){
        this.dataTracker.set (INSIGNIA_COLOR, insigniaColor);
    }

    public int getOutfitColor (){
        return this.dataTracker.get (OUTFIT_COLOR);
    }

    public void setOutfitColor (int outfitColor){
        this.dataTracker.set (OUTFIT_COLOR, outfitColor);
    }

    public abstract int defaultOutfitColor ();

    public abstract int defaultInsigniaColor ();

    public abstract GemPlacements[] getPlacements ();

    public int generateGemPlacement (){
        return this.getPlacements ()[this.random.nextInt (this.getPlacements ().length)].id;
    }

    public GemPlacements getGemPlacement (){
        return GemPlacements.getPlacement (this.dataTracker.get (GEM_PLACEMENT));
    }

    public void setGemPlacement (int gemPlacement){
        this.dataTracker.set (GEM_PLACEMENT, gemPlacement);
    }

    @Override
    public EntityData initialize (ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt){
        setPerfectionThings(this.getPerfection());
        setHairVariant (generateHairVariant ());
        this.setGemColorVariant (this.generateGemColorVariant ());
        if (this.setGemVariantOnInitialSpawn) {
            this.setGemColorVariant (this.generateGemColorVariant ());
            generateColors ();
        } else this.setGemColorVariant (this.initalGemColorVariant);
        setOutfitVariant (generateOutfitVariant ());
        setInsigniaVariant (generateInsigniaVariant ());
        //setOutfitColor (defaultOutfitColor ());
        //setInsigniaColor (defaultInsigniaColor ());
        setGemPlacement (generateGemPlacement ());
        return super.initialize (world, difficulty, spawnReason, entityData, entityNbt);
    }
    public float getPerfectionScaler(int perfection)
    {
        switch (perfection) {
            case 1 -> {
                return 0.8f;
            }
            case 2 -> {
                return 0.9f;
            }
            default -> {
                return 1f;
            }
            case 4 -> {
                return 1.075f;
            }
            case 5 -> {
                return 1.1f;
            }
            case 6 -> {
                return 1.125f;
            }
        }
    }
    @SuppressWarnings("DataFlowIssue")
    public void setPerfectionThings(int perfection)
    {
        this.refreshPosition();
        this.calculateDimensions();
        float multiplier = 1;
        if (getPerfection() >= 4)
        {
            multiplier = 1.25f;
        }
        else if (getPerfection() <= 2)
        {
            multiplier = 0.75f;
        }
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(maxHealth() * (getPerfectionScaler(perfection) * multiplier));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage() * (getPerfectionScaler(perfection) * multiplier));
    }
    public abstract int maxHealth ();
    public abstract int attackDamage();


    public void generateColors (){
        setHairColor (this.generatePaletteColor (PaletteType.HAIR));
        setSkinColor (this.generatePaletteColor (PaletteType.SKIN));
        setGemColor (this.generatePaletteColor (PaletteType.GEM));
    }

    public abstract int generateGemColorVariant ();

    public Text getName (){
        if (this instanceof AbstractVaryingGemEntity) {
            if (((AbstractVaryingGemEntity) this).UsesUniqueNames ()) {
                return Text.translatable ("entity.kindergartening." + this.getType ().getUntranslatedName () + "_" + this.getGemColorVariant ());
            }
        }
        return this.getDefaultName ();
    }
    @Override
    public int getXpToDrop() {
        return 0;
    }
    @Override public void onDeath (DamageSource source){
        if (!this.getWorld ().isClient ()) {
            ItemStack item = gemItem ();
            NbtCompound nbt = new NbtCompound ();
            nbt.putString ("id", EntityType.getId (this.getType ()).toString ());
            this.writeNbt (nbt);
            item.getOrCreateNbt ().put ("gem", nbt);
            Objects.requireNonNull (this.dropStack (item)).setNeverDespawn ();
        }
        else
        {
            this.getWorld().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPos().x, this.getPos().y, this.getPos().z, 1.0, 1.0, 1.0);
        }
        super.onDeath (source);
    }

    abstract public ItemStack gemItem ();

    @SuppressWarnings("OptionalGetWithoutIsPresent") public int generatePaletteColor (PaletteType type){
        String locString = type.type + "_palette";
        KinderMod.LOGGER.info ("[DEBUG] " + locString);
        ArrayList<Integer> colors = new ArrayList<> ();
        Identifier loc = new Identifier (KinderMod.MOD_ID + ":textures/entity/gems/" + this.getType ().getUntranslatedName () + "/palettes/" + locString + ".png");
        BufferedImage palette;
        try {
            palette = ImageIO.read (MinecraftClient.getInstance ().getResourceManager ().getResource (loc).get ().getInputStream ());
            KinderMod.LOGGER.info ("Palette Read!");
            for (int x = 0; x < palette.getWidth (); x++) {
                int color = palette.getRGB (x, this.getGemColorVariant ());
                if ((color >> 24) == 0x00) {
                    continue;
                }
                colors.add (color);
            }
        } catch (IOException e) {
            e.printStackTrace ();
            colors.add (0xFFFFFF);
        }
        return GemColors.lerpHex (colors);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source.getAttacker() == getOwner() && source.getAttacker() != null) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof DestabItem destabItem) {
                    float i = player.getAttackCooldownProgress(0f);
                    KinderMod.LOGGER.info(String.valueOf(i));
                    //if (i == 1f) {
                        if (destabItem == KinderItems.REJUVENATOR) {
                            boolean gr = this.getWorld().getGameRules().getBoolean(KinderMod.REJUVOTHERGEMS);
                            if (!gr && source.getAttacker() != this.getOwner())
                                {
                                    return super.damage(source,amount);
                                }
                            }
                            else {
                            setRebel(false);
                            if (getRebelBaseChance() >= 2) {
                                setRebelBaseChance(getRebelBaseChance() - 1);
                            }
                        }
                        return super.damage(this.getDamageSources().generic(), this.maxHealth() + 25);
                }
            }
            if (!this.isRebel()) {
                if (source.getAttacker().isSneaky()) {
                    return super.damage(this.getDamageSources().generic(), this.getMaxHealth() + 25);
                } else {
                    this.addRebelChance(0.2f);
                }
            }
        }
        return super.damage(source, amount);
    }

    /*
    Movement Type Values
    0 = Wander
    1 = Stay
    2 = Follow
    */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient || player.isSpectator()) {
            return super.interactMob(player, hand);
        }
        if (hand == Hand.MAIN_HAND) {

            if (getOwner() == null) {
                KinderMod.LOGGER.info("There's no owner.");
            }

            if (player != getOwner() && isTamed()) {
                player.sendMessage(Text.literal(getName().getString()).append(Text.translatable("kinder.gem.interact.notyours")));
                return ActionResult.SUCCESS;
            }

            if (player != getOwner() && getOwner() == null && !isRebel()) {
                setOwner(player);
                player.sendMessage(Text.literal(getName().getString()).append(Text.translatable("kinder.gem.interact.claimed")));
                return ActionResult.SUCCESS;
            }

            if (player == getOwner() && isRebel()) {
                player.sendMessage(Text.literal(getName().getString()).append(Text.translatable("kinder.gem.interact.rebel")));
                return ActionResult.SUCCESS;
            }

            if (player == getOwner()) {
                if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof DyeItem dye) {
                    int colorId = dye.getColor().getId();
                    if (player.isSneaking()) {
                        setOutfitColor(colorId);
                    } else {
                        setInsigniaColor(colorId);
                    }
                    return ActionResult.SUCCESS;
                }

                if (player.isSneaking() && player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                    byte movementType = (byte) ((dataTracker.get(MOVEMENT_TYPE) + 1) % 3);
                    FOLLOW_ID = (movementType == 2) ? player.getUuid() : null;
                    switch (movementType) {
                        case 0 -> player.sendMessage(Text.literal(getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.wander")));
                        case 1 -> player.sendMessage(Text.literal(getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.stay")));
                        case 2 -> player.sendMessage(Text.literal(getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.follow")));
                    }
                    dataTracker.set(MOVEMENT_TYPE, movementType);
                    return ActionResult.SUCCESS;
                } else if (!player.isSneaking()) {
                    interactGem(player);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.interactMob(player, hand);
    }
    public void interactGem (PlayerEntity player){}

    protected void updateInventory (){
        var previousInventory = this.inventory;
        this.inventory = new SimpleInventory (this.getInventorySize ());
        if (previousInventory != null) {
            previousInventory.removeListener (this);
            int maxSize = Math.min (previousInventory.size (), this.inventory.size ());

            for (int slot = 0; slot < maxSize; ++slot) {
                var stack = previousInventory.getStack (slot);
                if (!stack.isEmpty ()) {
                    this.inventory.setStack (slot, stack.copy ());
                }
            }
        }

        this.inventory.addListener (this);
    }

    public int getInventorySize (){
        return getPerfection () * 9;
    }

    public UUID getFollowId (){
        return FOLLOW_ID;
    }

    // Animation
    @Override public void registerControllers (AnimatableManager.ControllerRegistrar controllerRegistrar){
        controllerRegistrar.add (GemDefaultAnimations.genericGemWalkLegsController (this));
        controllerRegistrar.add (GemDefaultAnimations.genericGemWalkArmsController (this));
    }

    @Override public AnimatableInstanceCache getAnimatableInstanceCache (){
        return cache;
    }


    public byte getMovementType (){
        return this.dataTracker.get (MOVEMENT_TYPE);
    }

    public boolean isRebel (){
        return dataTracker.get (REBEL);
    }

    /* Sounds */
    @NotNull public abstract SoundEvent gemInstrument ();

    @Override public void playAmbientSound (){
        this.playSound (gemInstrument (), 1, this.getSoundPitch ());
    }

    public boolean canAttackWithOwner (LivingEntity target, LivingEntity owner){
        if (target instanceof Tameable) {
            if (target instanceof AbstractGemEntity && ((AbstractGemEntity) target).isRebel()) {
                return true;
            } else if (((Tameable) target).getOwner() == this.getOwner()) {
                return false;
            }
        }
        return !(target instanceof CreeperEntity);
    }

    @Nullable @Override public PassiveEntity createChild (ServerWorld world, PassiveEntity entity){
        return null;
    }

    @Override public EntityView method_48926 (){
        return this.getEntityWorld ();
    }

    public class GemScreenHandlerFactory implements ExtendedScreenHandlerFactory {
        private AbstractGemEntity gem (){
            return AbstractGemEntity.this;
        }

        @Override public Text getDisplayName (){
            return this.gem ().getDisplayName ();
        }

        @Override public ScreenHandler createMenu (int syncId, PlayerInventory inv, PlayerEntity player){
            var gemInv = this.gem ().inventory;
            return new PearlScreenHandler (syncId, inv, gemInv, this.gem (), this.gem ().getPerfection ());
        }

        @Override public void writeScreenOpeningData (ServerPlayerEntity player, PacketByteBuf buf){
            buf.writeVarInt (this.gem ().getId ());
        }
    }
}

