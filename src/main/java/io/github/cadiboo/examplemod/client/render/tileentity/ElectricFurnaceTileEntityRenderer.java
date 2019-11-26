package io.github.cadiboo.examplemod.client.render.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.cadiboo.examplemod.tileentity.ElectricFurnaceTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

/**
 * @author Cadiboo
 */
public class ElectricFurnaceTileEntityRenderer extends TileEntityRenderer<ElectricFurnaceTileEntity> {

	/**
	 * Render our TileEntity
	 */
	@Override
	public void render(final ElectricFurnaceTileEntity tileEntityIn, final double x, final double y, final double z, final float partialTicks, final int destroyStage) {
		super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);

		// Set up GL state
		RenderHelper.enableStandardItemLighting();

		GlStateManager.pushMatrix();

		// Translate to render pos. The 0.5 is to translate into the centre of the block, rather than to the corner of it
		GlStateManager.translated(x + 0.5, y, z + 0.5);

		// Translate to the place the item should be rendered (1/16 = the size of 1 pixel in the model)
		GlStateManager.translated(0, 1 / 16D, 0);

		// Render the item
		final ItemStack stackInInputSlot = tileEntityIn.inventory.getStackInSlot(ElectricFurnaceTileEntity.INPUT_SLOT);
		Minecraft.getInstance().getItemRenderer().renderItem(stackInInputSlot, TransformType.FIXED);

		GlStateManager.popMatrix();

		// Clean up GL state
		RenderHelper.disableStandardItemLighting();
	}

}
