package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.ModUtil;
import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import io.github.cadiboo.examplemod.tileentity.HeatCollectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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
import net.minecraftforge.items.ItemStackHandler;

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
		return ModTileEntityTypes.HEAT_COLLECTOR.get().create();
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
	 * Called on the logical server when a BlockState with a TileEntity is replaced by another BlockState.
	 * We use this method to drop all the items from our tile entity's inventory and update comparators near our block.
	 *
	 * @deprecated Call via {@link BlockState#onReplaced(World, BlockPos, BlockState, boolean)}
	 * Implementing/overriding is fine.
	 */
	@Override
	public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof HeatCollectorTileEntity) {
				final ItemStackHandler inventory = ((HeatCollectorTileEntity) tileEntity).inventory;
				for (int slot = 0; slot < inventory.getSlots(); ++slot)
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
			}
		}
		super.onReplaced(oldState, worldIn, pos, newState, isMoving);
	}

	/**
	 * Called when a player right clicks our block.
	 * We use this method to open our gui.
	 *
	 * @deprecated Call via {@link BlockState#onBlockActivated(World, PlayerEntity, Hand, BlockRayTraceResult)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public ActionResultType onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof HeatCollectorTileEntity)
				NetworkHooks.openGui((ServerPlayerEntity) player, (HeatCollectorTileEntity) tileEntity, pos);
		}
		return ActionResultType.SUCCESS;
	}

	/**
	 * We return the redstone calculated from our energy
	 *
	 * @deprecated call via {@link BlockState#getComparatorInputOverride(World, BlockPos)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		final TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof HeatCollectorTileEntity)
			return ModUtil.calcRedstoneFromEnergyStorage(((HeatCollectorTileEntity) tileEntity).energy);
		return super.getComparatorInputOverride(blockState, worldIn, pos);
	}

}
