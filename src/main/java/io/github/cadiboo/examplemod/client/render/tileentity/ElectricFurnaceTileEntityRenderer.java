package io.github.cadiboo.examplemod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.cadiboo.examplemod.config.ExampleModConfig;
import io.github.cadiboo.examplemod.tileentity.ElectricFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

/**
 * @author Cadiboo
 */
public class ElectricFurnaceTileEntityRenderer extends TileEntityRenderer<ElectricFurnaceTileEntity> {

	public ElectricFurnaceTileEntityRenderer(final TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
	}

	/**
	 * Render our TileEntity
	 */
	@Override
	public void func_225616_a_(final ElectricFurnaceTileEntity tileEntityIn, final float partialTicks, final MatrixStack matrixStack, final IRenderTypeBuffer renderTypeBuffer, final int packedLight, final int backupPackedLight) {

		// Set up GL state
//		RenderHelper.enableStandardItemLighting();
		RenderHelper.func_227780_a_();

		final boolean hasEnergy = tileEntityIn.energy.getEnergyStored() >= ExampleModConfig.electricFurnaceEnergySmeltCostPerTick;
		final BlockState renderState = Blocks.REDSTONE_TORCH.getDefaultState()
				.with(RedstoneTorchBlock.LIT, hasEnergy);
		// Render the torch (We use the depreciated method because we don't have an IModelData instance and want to use the default one)
		Minecraft.getInstance().getBlockRendererDispatcher()
				.func_228791_a_(renderState, matrixStack, renderTypeBuffer, packedLight, backupPackedLight);

		// Clean up GL state
		RenderHelper.disableStandardItemLighting();
	}

}
