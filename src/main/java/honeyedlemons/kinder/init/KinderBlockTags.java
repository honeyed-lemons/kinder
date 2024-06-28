package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class KinderBlockTags {
    public static final TagKey<Block> DRAINABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(KinderMod.MOD_ID, "drainable"));
}