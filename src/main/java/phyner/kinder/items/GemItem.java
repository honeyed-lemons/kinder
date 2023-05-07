package phyner.kinder.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.entities.gems.RubyEntity;
import phyner.kinder.init.KinderEntities;

import java.util.Optional;

public class GemItem extends Item {
    public GemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getSide().equals(Direction.UP)) {
            this.spawnGem(context.getStack(), context.getWorld(), context.getBlockPos());
        }
        return ActionResult.CONSUME;
    }

    public void spawnGem(ItemStack itemStack, World world, BlockPos pos)
    {
        NbtCompound nbt = itemStack.getSubNbt("gem");
        if (!world.isClient)
        {
            Optional<Entity> entity = EntityType.getEntityFromNbt(nbt,world);
            if (entity.isPresent())
            {
                AbstractGemEntity gem = (AbstractGemEntity) entity.get();
                gem.setPos(pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5F);
                gem.fallDistance = 0;
                gem.speed = 0;
                gem.setOnFire(false);
                gem.setHealth(gem.getMaxHealth());
                gem.clearStatusEffects();
                gem.setVelocity(0,0,0);
                KinderMod.LOGGER.info("Spawning Gem, Name is "+gem.getName().getString());
                world.spawnEntity(gem);
            }
        }
    }
}
