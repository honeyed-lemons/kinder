package honeyedlemons.kinder;

import honeyedlemons.kinder.init.KinderBlocks;
import honeyedlemons.kinder.init.KinderFluidHandling;
import honeyedlemons.kinder.init.KinderGemEntities;
import honeyedlemons.kinder.init.KinderScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT) public class KinderModClient implements ClientModInitializer {

    @Override public void onInitializeClient (){
        KinderGemEntities.registerEntityRenderers();
        KinderScreens.clientint();
        KinderScreens.init ();
        KinderBlocks.setBlockRender();
        KinderFluidHandling.fluidRendering();
    }
}