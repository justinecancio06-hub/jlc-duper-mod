package com.example.jlcduper.client.screen;

import com.example.jlcduper.screen.JLCDuperScreenHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class JLCDuperScreen extends AbstractContainerScreen<JLCDuperScreenHandler> {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/container/furnace.png");
	private static final Identifier LIT_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("minecraft", "container/furnace/lit_progress");
	private static final Identifier BURN_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("minecraft", "container/furnace/burn_progress");

	public JLCDuperScreen(JLCDuperScreenHandler menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.inventoryLabelX = 8;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractBackground(graphics, mouseX, mouseY, delta);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;

		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F,
				this.imageWidth, this.imageHeight, 256, 256);

		int progress = Math.max(0, this.menu.getProgress());
		int maxProgress = Math.max(1, this.menu.getMaxProgress());
		float fraction = (float) progress / maxProgress;

		if (progress > 0) {
			int flameHeight = Mth.ceil(fraction * 13.0F) + 1;
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE,
					14, 14, 0, 14 - flameHeight, x + 56, y + 36 + 14 - flameHeight, 14, flameHeight);
		}

		int arrowWidth = Mth.ceil(fraction * 24.0F);
		if (arrowWidth > 0) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE,
					24, 16, 0, 0, x + 79, y + 34, arrowWidth, 16);
		}
	}
}
