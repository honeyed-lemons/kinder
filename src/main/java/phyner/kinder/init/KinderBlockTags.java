package phyner.kinder.init;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;

public class KinderBlockTags {
    public static final TagKey<Block> DRAINABLE = TagKey.of(RegistryKeys.BLOCK,new Identifier(KinderMod.MOD_ID,"drainable"));
}