package phyner.kinder.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.items.GemItem;

public class KinderItems {
    public static final Item RUBY_GEM = new GemItem(new FabricItemSettings());

    public static void registerItems()
    {
        Registry.register(Registries.ITEM, new Identifier(KinderMod.MOD_ID,"ruby_gem"),RUBY_GEM);
    }
}
