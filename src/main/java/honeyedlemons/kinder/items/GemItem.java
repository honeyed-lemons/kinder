package honeyedlemons.kinder.items;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GemItem extends Item {
    private final EntityType<?> type;
    private final GemColors color;

    public GemItem(EntityType<? extends AbstractGemEntity> type, GemColors color, Properties settings) {
        super(settings);
        this.type = type;
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace().equals(Direction.UP)) {
            this.spawnGem(context.getItemInHand(), context.getLevel(), context.getClickedPos(), context);
        }
        return InteractionResult.CONSUME;
    }

    public GemColors getColor() {
        return color;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    public void spawnGem(ItemStack itemStack, Level world, BlockPos pos, UseOnContext context) {
        CompoundTag nbt = itemStack.getTagElement("gem");
        if (!world.isClientSide) {
            if (context.getPlayer() != null) {
                List<AbstractGemEntity> list = context.getPlayer().level().getEntitiesOfClass(AbstractGemEntity.class, context.getPlayer().getBoundingBox().inflate(4, 4, 4), EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                for (AbstractGemEntity gem : list) {
                    if (gem.isDeadOrDying()) {
                        KinderMod.LOGGER.info("Cant Spawn Gem lmao get rekt");
                        return;
                    }
                }
            }
            if (nbt != null) {
                Optional<Entity> entity = EntityType.create(nbt, world);
                if (entity.isPresent()) {
                    AbstractGemEntity gem = (AbstractGemEntity) entity.get();
                    gem.setPosRaw(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5F);
                    gem.fallDistance = 0;
                    gem.flyDist = 0;
                    gem.setSharedFlagOnFire(false);
                    gem.setRemainingFireTicks(0);
                    gem.setHealth(gem.getMaxHealth());
                    gem.removeAllEffects();
                    gem.setDeltaMovement(0, 0, 0);
                    KinderMod.LOGGER.info("Spawning Gem, Name is " + gem.getName().getString());
                    world.addFreshEntity(gem);
                    gem.lookAt(context.getPlayer(), 90, 90);
                    itemStack.setCount(0);
                }
            } else {
                {
                    if (itemStack.getTag() != null) {
                        int perf = itemStack.getTag().getInt("Perfection");
                        spawnGemWONbt(itemStack, world, pos, context, perf);
                    } else {
                        spawnGemWONbt(itemStack, world, pos, context, 3);
                    }
                }
            }
        }
    }

    public void spawnGemWONbt(ItemStack itemStack, Level world, BlockPos pos, UseOnContext context, int perfection) {
        AbstractGemEntity gem = (AbstractGemEntity) Objects.requireNonNull(type.spawn(Objects.requireNonNull(Objects.requireNonNull(world.getServer()).getLevel(world.dimension())), pos.above(), MobSpawnType.MOB_SUMMONED));
        gem.setGemVariantOnInitialSpawn = false;
        gem.setGemColorVariant(color.getId());
        gem.setPerfection(perfection);
        gem.setPerfectionThings(perfection);
        KinderMod.LOGGER.info("Spawning Gem, Name is " + type.getDescription().getString() + " with a perfection of " + perfection);
        if (!Objects.requireNonNull(context.getPlayer()).isCreative()) {
            itemStack.setCount(0);
        }
        gem.generateColors();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        CompoundTag nbt = stack.getTagElement("gem");
        if (nbt != null && world != null) {
            Optional<Entity> entity = EntityType.create(nbt, world);
            entity.ifPresent(value -> tooltip.add(Component.nullToEmpty(value.getName().getString())));
        }
    }
}
