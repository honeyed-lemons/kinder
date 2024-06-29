package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.client.render.screens.PearlScreen;
import honeyedlemons.kinder.client.render.screens.handlers.PearlScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class KinderScreens {
    public static void init() {
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(KinderMod.MOD_ID, "pearlinventory"), PEARL_SCREEN_HANDLER);
    }

    public static void clientint() {
        MenuScreens.register(PEARL_SCREEN_HANDLER, PearlScreen::new);
    }

    public static final MenuType<PearlScreenHandler> PEARL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(PearlScreenHandler::new);


}
