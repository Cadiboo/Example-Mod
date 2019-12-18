package io.github.cadiboo.examplemod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.container.ElectricFurnaceContainer;
import io.github.cadiboo.examplemod.tileentity.ElectricFurnaceTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.EnergyStorage;

/**
 * @author Cadiboo
 */
public class ElectricFurnaceScreen extends ContainerScreen<ElectricFurnaceContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ExampleMod.MODID, "textures/gui/container/electric_furnace.png");

	public ElectricFurnaceScreen(final ElectricFurnaceContainer container, final PlayerInventory inventory, final ITextComponent title) {
		super(container, inventory, title);
	}

	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

		int relMouseX = mouseX - this.guiLeft;
		int relMouseY = mouseY - this.guiTop;
		final ElectricFurnaceTileEntity tileEntity = this.container.tileEntity;
		boolean energyBarHovered = relMouseX > 151 && relMouseX < 166 && relMouseY > 10 && relMouseY < 76;
		if (energyBarHovered) {
			String tooltip = new TranslationTextComponent(
					"gui." + ExampleMod.MODID + ".energy",
					tileEntity.energy.getEnergyStored()
			).getFormattedText();
			this.renderTooltip(tooltip, mouseX, mouseY);
		}
		boolean arrowHovered = relMouseX > 79 && relMouseX < 104 && relMouseY > 34 && relMouseY < 50;
		if (arrowHovered && tileEntity.maxSmeltTime > 0) {
			String tooltip = new TranslationTextComponent(
					"gui." + ExampleMod.MODID + ".smeltTimeProgress",
					tileEntity.smeltTimeLeft, tileEntity.maxSmeltTime
			).getFormattedText();
			this.renderTooltip(tooltip, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		// Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
		String s = this.title.getFormattedText();
		this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int startX = this.guiLeft;
		int startY = this.guiTop;

		// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
		// The parameters are (x, y, u, v, width, height)

		this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

		final ElectricFurnaceTileEntity tileEntity = container.tileEntity;
		if (tileEntity.energy.getEnergyStored() > 0) { // Draw energy bar
			int energyProgress = getEnergyProgressScaled();
			this.blit(
					startX + 152, startY + 10 + 65 - energyProgress,
					176, 16,
					14, energyProgress
			);
		}
		if (tileEntity.smeltTimeLeft > 0) {
			// Draw progress arrow
			int arrowWidth = getSmeltTimeScaled();
			this.blit(
					startX + 79, startY + 34,
					176, 0,
					arrowWidth, 16
			);
		}
	}

	private int getEnergyProgressScaled() {
		final ElectricFurnaceTileEntity tileEntity = this.container.tileEntity;
		final EnergyStorage energy = tileEntity.energy;
		final int energyStored = energy.getEnergyStored();
		final int maxEnergyStored = energy.getMaxEnergyStored();
		return Math.round((float) energyStored / maxEnergyStored * 65); // 65 is the height of the arrow
	}

	private int getSmeltTimeScaled() {
		final ElectricFurnaceTileEntity tileEntity = this.container.tileEntity;
		final short smeltTimeLeft = tileEntity.smeltTimeLeft;
		final short maxSmeltTime = tileEntity.maxSmeltTime;
		if (smeltTimeLeft <= 0 || maxSmeltTime <= 0)
			return 0;
		return (maxSmeltTime - smeltTimeLeft) * 24 / maxSmeltTime; // 24 is the width of the arrow
	}

}
