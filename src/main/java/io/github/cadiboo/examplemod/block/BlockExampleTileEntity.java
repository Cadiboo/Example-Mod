package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.tileentity.TileEntityExampleTileEntity;
import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The Block for {@link TileEntityExampleTileEntity}
 *
 * @author Cadiboo
 */
public class BlockExampleTileEntity extends Block {

	public BlockExampleTileEntity(@Nonnull final String name) {
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setHardness(1);
	}

	@Override
	@Nonnull
	public EnumBlockRenderType getRenderType(@Nonnull final IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isOpaqueCube(@Nonnull final IBlockState state) {
		return false;
	}

	@Override
	public boolean hasTileEntity(@Nonnull final IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntityExampleTileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) {
		return new TileEntityExampleTileEntity();
	}

}
