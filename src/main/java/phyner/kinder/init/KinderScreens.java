package phyner.kinder.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import phyner.kinder.client.render.screens.PearlScreen;
import phyner.kinder.client.render.screens.handlers.PearlScreenHandler;

public class KinderScreens {
    public static final ScreenHandlerType<PearlScreenHandler> PEARL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(PearlScreenHandler::new);
    public static void init()
    {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("kindergarten","peerlInventory"), PEARL_SCREEN_HANDLER);
    }

    public static void clientint()
    {
        HandledScreens.register(PEARL_SCREEN_HANDLER,
                PearlScreen::new);
    }
}
