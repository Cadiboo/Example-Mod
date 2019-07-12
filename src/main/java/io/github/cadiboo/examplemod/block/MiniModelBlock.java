package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
public class MiniModelBlock extends Block {

	public MiniModelBlock(final Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(final BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
		// Always use TileEntityType#create to allow registry overrides to work.
		return ModTileEntityTypes.MINI_MODEL.create();
	}

	/**
	 * @deprecated Call via {@link BlockState#isSolid()} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public boolean isSolid(final BlockState state) {
		// This prevents xraying through the world and allows light to go through this block.
		return false;
	}

	/**
	 * @deprecated Call via {@link BlockState#getCollisionShape(IBlockReader, BlockPos, ISelectionContext)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public VoxelShape getCollisionShape(final BlockState state, final IBlockReader reader, final BlockPos pos, final ISelectionContext context) {
		// Allow entities to walk through the model
		return VoxelShapes.empty();
	}

}
