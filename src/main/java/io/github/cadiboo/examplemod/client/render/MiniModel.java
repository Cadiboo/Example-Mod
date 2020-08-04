package io.github.cadiboo.examplemod.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.ChunkRender;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * A wrapper around ChunkRender.
 * Stores the a render of the chunk (16x16x16) surrounding a TileEntity
 * TODO: Make this work on 1.15
 *
 * @author Cadiboo
 */
public class MiniModel {

	/**
	 * We only create one of these per cache, we reset it each time we rebuild.
	 * This holds all the rendered data.
	 */
	public final RegionRenderCacheBuilder regionRenderCacheBuilder;
	/**
	 * We use vanilla's code to do the complex rendering through this.
	 * It is the driver that handles actually rendering everything.
	 */
	private final ChunkRender chunkRender;
	/**
	 * If this MiniModel has had it's data built.
	 */
	private boolean isCompiled = false;
	/**
	 * This stores data about which render types contain data so that we only render
	 * buffers that we used instead of expensively rendering everything.
	 */
	private ChunkRenderDispatcher.CompiledChunk compiledChunk = ChunkRenderDispatcher.CompiledChunk.DUMMY;

	private MiniModel(final ChunkRender chunkRender, final RegionRenderCacheBuilder regionRenderCacheBuilder) {
		this.chunkRender = chunkRender;
		this.regionRenderCacheBuilder = regionRenderCacheBuilder;
	}

	public static MiniModel forTileEntity(final TileEntity tileEntity) {
		// The "renderDispatcher" field was made public by our AT
		final ChunkRender chunkRender = Minecraft.getInstance().worldRenderer.renderDispatcher.new ChunkRender();
		final BlockPos pos = tileEntity.getPos();

		// We want to render everything in a 16x16x16 radius, with the centre being the TileEntity
		chunkRender.setPosition(pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8);

		return new MiniModel(chunkRender, new RegionRenderCacheBuilder());
	}

	public ChunkRenderDispatcher.CompiledChunk getCompiledChunk() {
		return compiledChunk;
	}

	/**
	 * Uses Minecraft's code to render the surrounding blocks to the {@link #regionRenderCacheBuilder}
	 * and fill our {@link #compiledChunk}.
	 * TODO: FIXME: This doesn't work
	 */
	public void compile() {
		final RegionRenderCacheBuilder buffers = this.regionRenderCacheBuilder;

		// Reset the buffers
		buffers.discardBuilders();

		// Rebuild the ChunkRender
		this.compiledChunk = new ChunkRenderDispatcher.CompiledChunk();
		// The "renderDispatcher" field was made public by our AT
		Vec3d vec3d = Minecraft.getInstance().worldRenderer.renderDispatcher.getRenderPosition();
		float x = (float) vec3d.x;
		float y = (float) vec3d.y;
		float z = (float) vec3d.z;
		// The "RebuildTask" class was made public by our AT
		final ChunkRender.RebuildTask rebuildTask = (ChunkRender.RebuildTask) this.chunkRender.makeCompileTaskChunk();
		// The "compile" method was made public by our AT
		// It renders every block in the chunk to the buffers
		rebuildTask.compile(x, y, z, this.compiledChunk, buffers);

		this.isCompiled = true;
	}

	/**
	 * @return If this MiniModel has had its data built
	 */
	public boolean isCompiled() {
		return isCompiled;
	}

}
