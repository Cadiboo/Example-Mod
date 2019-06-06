package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
public class BlockExampleTileEntity extends Block {

	public BlockExampleTileEntity(final Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(final IBlockState state, final IBlockReader world) {
		return ModTileEntityTypes.EXAMPLE_TILE_ENTITY.create();
	}

}
