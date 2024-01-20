package phyner.kinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import phyner.kinder.client.render.gems.PearlEntityRenderer;
import phyner.kinder.client.render.gems.QuartzEntityRenderer;
import phyner.kinder.client.render.gems.RubyEntityRenderer;
import phyner.kinder.init.KinderGemEntities;
import phyner.kinder.init.KinderScreens;

@Environment(EnvType.CLIENT)
public class KinderModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(KinderGemEntities.RUBY, RubyEntityRenderer::new);
        EntityRendererRegistry.register(KinderGemEntities.QUARTZ, QuartzEntityRenderer::new);
        EntityRendererRegistry.register(KinderGemEntities.PEARL, PearlEntityRenderer::new);
        KinderScreens.clientint();
    }
}