package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.client.render.screens.PearlScreen;
import honeyedlemons.kinder.client.render.screens.handlers.PearlScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class KinderScreens {
    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(KinderMod.MOD_ID, "pearlinventory"), PEARL_SCREEN_HANDLER);
    }

    public static void clientint() {
        HandledScreens.register(PEARL_SCREEN_HANDLER, PearlScreen::new);
    }

    public static final ScreenHandlerType<PearlScreenHandler> PEARL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(PearlScreenHandler::new);


}
