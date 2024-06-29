package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class KinderBlockTags {
    public static final TagKey<Block> DRAINABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(KinderMod.MOD_ID, "drainable"));
}