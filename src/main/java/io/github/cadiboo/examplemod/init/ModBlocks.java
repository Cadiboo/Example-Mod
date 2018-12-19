package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.block.BlockExampleTileEntity;
import io.github.cadiboo.examplemod.block.BlockModOre;
import io.github.cadiboo.examplemod.block.BlockResource;
import io.github.cadiboo.examplemod.util.ModReference;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Block Instances class
 * All the blocks in here will be public static final and null as their values will be filled by the magical @ObjectHolder
 *
 * @author Cadiboo
 */
@ObjectHolder(ModReference.MOD_ID)
public final class ModBlocks {

	public static final BlockResource EXAMPLE_BLOCK = null;
	public static final BlockModOre EXAMPLE_ORE = null;
	public static final BlockExampleTileEntity EXAMPLE_TILE_ENTITY = null;

}
