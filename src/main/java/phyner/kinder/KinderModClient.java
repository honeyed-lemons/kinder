package phyner.kinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import phyner.kinder.client.render.gems.PearlEntityRenderer;
import phyner.kinder.client.render.gems.QuartzEntityRenderer;
import phyner.kinder.client.render.gems.RubyEntityRenderer;
import phyner.kinder.client.render.gems.SapphireEntityRenderer;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderGemEntities;
import phyner.kinder.init.KinderScreens;

@Environment(EnvType.CLIENT) public class KinderModClient implements ClientModInitializer {

    @Override public void onInitializeClient (){
        KinderGemEntities.registerEntityRenderers();
        KinderScreens.clientint();
        KinderBlocks.setBlockRender();
    }
}