/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package honeyedlemons.kinder.client.render.screens;

import honeyedlemons.kinder.client.render.screens.handlers.PearlScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment (value = EnvType.CLIENT)
public class PearlScreen extends AbstractContainerScreen<PearlScreenHandler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private final int rows;

    public PearlScreen(PearlScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.rows = handler.entity.getPerfection();
        this.imageHeight = 114 + this.rows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        context.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.rows * 18 + 17);
        context.blit(TEXTURE, i, j + this.rows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}

