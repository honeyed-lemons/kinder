package phyner.kinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import phyner.kinder.client.render.gems.RubyEntityRenderer;
import phyner.kinder.init.KinderEntities;

@Environment(EnvType.CLIENT)
public class KinderModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(KinderEntities.RUBY, RubyEntityRenderer::new);
    }
}