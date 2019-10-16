package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import io.github.cadiboo.examplemod.tileentity.HeatCollectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

/**
 * @author Cadiboo
 */
public class HeatCollectorBlock extends Block {

	private static final VoxelShape SHAPE = Stream.of(
			Block.makeCuboidShape(11, 0, 0, 16, 16, 5),
			Block.makeCuboidShape(11, 0, 11, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 11, 5, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 5, 16, 5),
			Block.makeCuboidShape(5, 5, 5, 11, 11, 11)
	)
			.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR))
			.get();

	public HeatCollectorBlock(final Properties properties) {
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
		return ModTileEntityTypes.HEAT_COLLECTOR.create();
	}

	/**
	 * @deprecated Call via {@link BlockState#getShape(IBlockReader, BlockPos, ISelectionContext)}
	 * Implementing/overriding is fine.
	 */
	@Nonnull
	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		return SHAPE;
	}

	/**
	 * @deprecated Call via {@link BlockState#onBlockActivated(World, PlayerEntity, Hand, BlockRayTraceResult)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public boolean onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof HeatCollectorTileEntity) {
				NetworkHooks.openGui(((ServerPlayerEntity) player), ((HeatCollectorTileEntity) tileEntity), pos);
			}
		}
		return true;
	}

}
