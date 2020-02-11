package io.github.cadiboo.examplemod.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cadiboo.examplemod.client.render.MiniModel;
import io.github.cadiboo.examplemod.config.ExampleModConfig;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

/**
 * Handles rendering all MiniModel TileEntities.
 * The render method is called once each frame for every visible MiniModel.
 * <p>
 * Renders a model of the surrounding blocks.
 * This should really probably not be in an examplemod for beginners,
 * but I added comments to it so its all good
 *
 * TODO: Update this to 1.15
 *
 * @author Cadiboo
 */
public class MiniModelTileEntityRenderer extends TileEntityRenderer<MiniModelTileEntity> {

	public MiniModelTileEntityRenderer(final TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
	}

	/**
	 * Render our TileEntity
	 */
	@Override
	public void render(final MiniModelTileEntity tileEntityIn, final float partialTicks, final MatrixStack matrixStack, final IRenderTypeBuffer renderTypeBuffer, final int packedLight, final int backupPackedLight) {

//		final MiniModel miniModel = tileEntityIn.miniModel;
//
//		if (miniModel == null)
//			return;
//
//		if (!miniModel.isBuilt())
//			miniModel.rebuild();
//
//		// Setup correct GL state
////		this.field_228858_b_.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//		RenderHelper.disableStandardItemLighting();
//		// Translucency
//		if (ExampleModConfig.modelTranslucency) {
//			RenderSystem.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
//		} else {
//			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		}
//		RenderSystem.enableBlend();
//
//		if (Minecraft.isAmbientOcclusionEnabled()) {
//			RenderSystem.shadeModel(GL11.GL_SMOOTH);
//		} else {
//			RenderSystem.shadeModel(GL11.GL_FLAT);
//		}
//
//		GlStateManager.pushMatrix();
//
//		// Translate to render pos. The 0.5 is to translate into the centre of the block, rather than to the corner of it
//		GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
//
//		final double scale = ExampleModConfig.modelScale;
//		GlStateManager.scaled(scale, scale, scale);
//
//		// Translate to start of render (our TileEntity is at its centre)
//		GlStateManager.translated(-8, -8, -8);
//
//		// Render the buffers
//		renderChunkBuffers(miniModel.regionRenderCacheBuilder, miniModel.generator.getCompiledChunk());
//
//		GlStateManager.popMatrix();
//
//		// Clean up GL state
//		RenderHelper.enableStandardItemLighting();

	}

	/**
	 * This renderer is a global renderer.
	 * This means that it will always render, even if the player is not able to see it's block.
	 * This is useful for rendering larger models or dynamically sized models.
	 * The Beacon's beam is also a global renderer
	 */
	@Override
	public boolean isGlobalRenderer(final MiniModelTileEntity te) {
		return true;
	}

//	/**
//	 * Loops through every non-empty {@link BufferBuilder} in buffers and renders the buffer without resetting it
//	 *
//	 * @param buffers       The {@link RegionRenderCacheBuilder} to get {@link BufferBuilder}s from
//	 * @param compiledChunk The {@link CompiledChunk} to use to check if a layer has any rendered blocks
//	 */
//	private void renderChunkBuffers(final RegionRenderCacheBuilder buffers, final CompiledChunk compiledChunk) {
//		final int length = BLOCK_RENDER_LAYERS.length;
//		// Render each buffer that has been used
//		for (int layerOrdinal = 0; layerOrdinal < length; ++layerOrdinal) {
//			if (!compiledChunk.isLayerEmpty(BLOCK_RENDER_LAYERS[layerOrdinal])) {
//				drawBufferWithoutResetting(buffers.getBuilder(layerOrdinal));
//			}
//		}
//	}
//
//	/**
//	 * This should work.
//	 * Draws a BufferBuilder without resetting its internal data.
//	 *
//	 * @param bufferBuilder The BufferBuilder to draw (but not reset)
//	 */
//	private void drawBufferWithoutResetting(final BufferBuilder bufferBuilder) {
//		// Get the internal data from the BufferBuilder (This resets the BufferBuilder's own copy of this data)
//		final ByteBuffer byteBuffer = bufferBuilder.getAndResetData().getSecond();
//		// Set the BufferBuilder's internal data to the original data
//		bufferBuilder.putBulkData(byteBuffer);
//		// Draw the BufferBuilder (This resets the BufferBuilder's data)
//		WorldVertexBufferUploader.draw(bufferBuilder);
//		// Set the BufferBuilder's internal data back to the original data
//		bufferBuilder.putBulkData(byteBuffer);
//	}

}
