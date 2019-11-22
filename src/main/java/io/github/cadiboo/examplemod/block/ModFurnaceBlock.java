package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import io.github.cadiboo.examplemod.tileentity.ModFurnaceTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
public class ModFurnaceBlock extends Block {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");

	public ModFurnaceBlock(final Properties properties) {
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
		return ModTileEntityTypes.MOD_FURNACE.create();
	}

	/**
	 * Called on the logical server when a BlockState with a TileEntity is replaced by another BlockState
	 *
	 * @deprecated Call via {@link BlockState#onReplaced(World, BlockPos, BlockState, boolean)}
	 * Implementing/overriding is fine.
	 */
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof ModFurnaceTileEntity) {
				final ItemStackHandler inventory = ((ModFurnaceTileEntity) tileEntity).inventory;
				for (int slot = 0; slot < inventory.getSlots(); ++slot) {
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
				}
			}
		}
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}

	/**
	 * @deprecated Call via {@link BlockState#onBlockActivated(World, PlayerEntity, Hand, BlockRayTraceResult)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Override
	public boolean onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof ModFurnaceTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (ModFurnaceTileEntity) tileEntity, pos);
			}
		}
		return true;
	}

}
