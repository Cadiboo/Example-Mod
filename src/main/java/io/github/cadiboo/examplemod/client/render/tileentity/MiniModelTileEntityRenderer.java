package io.github.cadiboo.examplemod.client.render.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.cadiboo.examplemod.config.ExampleModConfig;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.chunk.ChunkRender;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Renders a model of the surrounding blocks.
 * This should really probably not be in an examplemod for beginners,
 * but I added comments to it so its all good
 *
 * @author Cadiboo
 */
public class MiniModelTileEntityRenderer extends TileEntityRenderer<MiniModelTileEntity> {

	/**
	 * We use a WeakHashMap so that we don't hold on to ExampleTileEntityTileEntitys after they have been removed
	 */
	private final WeakHashMap<MiniModelTileEntity, RenderCache> map = new WeakHashMap<>();

	/**
	 * Render our TileEntity
	 */
	@Override
	public void render(final MiniModelTileEntity tileEntityIn, final double x, final double y, final double z, final float partialTicks, final int destroyStage) {
		super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);
		if (!map.containsKey(tileEntityIn)) {
			// Calculate the render if it doesn't exist
			final World world = tileEntityIn.getWorld();
			if (world == null) {
				return;
			}
			map.put(tileEntityIn, RenderCache.from(world, tileEntityIn.getPos()));
		} else {
			// Recalculate the render if its older than 5 seconds
			final RenderCache cachedRender = map.get(tileEntityIn);
			final long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - cachedRender.timeLastUpdated > 5_000) {
				cachedRender.timeLastUpdated = currentTimeMillis;
				cachedRender.rebuild();
			}
		}

		// Setup correct GL state
		this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		// Translucency
		if (ExampleModConfig.modelTranslucency) {
			GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
		} else {
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		GlStateManager.enableBlend();

		if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		} else {
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		GlStateManager.pushMatrix();

		// Translate to render pos. The 0.5 is to translate into the centre of the block, rather than to the corner of it
		GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);

		final double scale = ExampleModConfig.modelScale;
		GlStateManager.scaled(scale, scale, scale);

		// Translate to start of render (our TileEntity is at its centre)
		GlStateManager.translated(-8, -8, -8);

		// Render the buffers
		final RenderCache renderCache = map.get(tileEntityIn);
		renderChunkBuffers(renderCache.regionRenderCacheBuilder, renderCache.generator.getCompiledChunk());

		GlStateManager.popMatrix();

		// Clean up GL state
		RenderHelper.enableStandardItemLighting();

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

	private void renderChunkBuffers(final RegionRenderCacheBuilder buffers, final CompiledChunk compiledChunk) {
		final BlockRenderLayer[] blockRenderLayers = BlockRenderLayer.values();
		final int length = blockRenderLayers.length;
		// Render each buffer that has been used
		for (int ordinal = 0; ordinal < length; ++ordinal) {
			if (!compiledChunk.isLayerEmpty(blockRenderLayers[ordinal])) {
				drawBufferWithoutResetting(buffers.getBuilder(ordinal));
			}
		}
	}

	/**
	 * Copy of net.minecraft.client.renderer.WorldVertexBufferUploader#draw(net.minecraft.client.renderer.BufferBuilder)
	 * The only difference is that it does NOT reset the buffer after drawing it
	 *
	 * @param bufferBuilderIn the buffer builder to draw
	 */
	private void drawBufferWithoutResetting(final BufferBuilder bufferBuilderIn) {
		if (bufferBuilderIn.getVertexCount() > 0) {
			VertexFormat vertexformat = bufferBuilderIn.getVertexFormat();
			int i = vertexformat.getSize();
			ByteBuffer bytebuffer = bufferBuilderIn.getByteBuffer();
			List<VertexFormatElement> list = vertexformat.getElements();

			for (int j = 0; j < list.size(); ++j) {
				VertexFormatElement vertexformatelement = list.get(j);
				vertexformatelement.getUsage().preDraw(vertexformat, j, i, bytebuffer); // moved to VertexFormatElement.preDraw
			}

			GlStateManager.drawArrays(bufferBuilderIn.getDrawMode(), 0, bufferBuilderIn.getVertexCount());
			int i1 = 0;

			for (int j1 = list.size(); i1 < j1; ++i1) {
				VertexFormatElement vertexformatelement1 = list.get(i1);
				vertexformatelement1.getUsage().postDraw(vertexformat, i1, i, bytebuffer); // moved to VertexFormatElement.postDraw
			}
		}

		// Commented out - don't reset the buffer
//		bufferBuilderIn.reset();
	}

	private static class RenderCache {

		private final ChunkRender chunkRender;
		// We only create one of these per cache, we reset it each time we rebuild
		private final RegionRenderCacheBuilder regionRenderCacheBuilder;
		private ChunkRenderTask generator;
		private long timeLastUpdated;

		private RenderCache(final ChunkRender chunkRender, final RegionRenderCacheBuilder regionRenderCacheBuilder) {
			this.chunkRender = chunkRender;
			this.regionRenderCacheBuilder = regionRenderCacheBuilder;
			rebuild();
			this.timeLastUpdated = System.currentTimeMillis();
		}

		private static RenderCache from(final World world, final BlockPos pos) {
			final ChunkRender chunkRender = new ChunkRender(world, Minecraft.getInstance().worldRenderer);
			// We want to render everything in a 16x16x16 radius, with the centre being our TileEntity
			chunkRender.setPosition(pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8);

			final RegionRenderCacheBuilder regionRenderCacheBuilder = new RegionRenderCacheBuilder();

			return new RenderCache(chunkRender, regionRenderCacheBuilder);
		}

		/**
		 * (re)build the render
		 */
		private void rebuild() {
			final ChunkRender chunkRender = this.chunkRender;
			final RegionRenderCacheBuilder buffers = this.regionRenderCacheBuilder;

			final ChunkRenderTask generator = chunkRender.makeCompileTaskChunk();
			this.generator = generator;

			final BlockRenderLayer[] blockRenderLayers = BlockRenderLayer.values();
			final int length = blockRenderLayers.length;
			// Reset regionRenderCacheBuilder
			for (int ordinal = 0; ordinal < length; ++ordinal) {
				buffers.getBuilder(ordinal).reset();
			}

			// Setup generator
			generator.setStatus(ChunkRenderTask.Status.COMPILING);
			generator.setRegionRenderCacheBuilder(buffers);

			// TODO: when mappings update, func_215316_n->getActiveRenderInfo and func_216785_c->getProjectedView
//		    final Vec3d vec3d = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
			final Vec3d vec3d = Minecraft.getInstance().gameRenderer.func_215316_n().func_216785_c();

			chunkRender.rebuildChunk((float) vec3d.x, (float) vec3d.y, (float) vec3d.z, generator);
			// rebuildChunk increments this, we don't want to increment it
			--ChunkRender.renderChunksUpdated;

			// Set the translation of each buffer back to 0
			for (int ordinal = 0; ordinal < length; ++ordinal) {
				buffers.getBuilder(ordinal).setTranslation(0, 0, 0);
			}

		}

	}

}
