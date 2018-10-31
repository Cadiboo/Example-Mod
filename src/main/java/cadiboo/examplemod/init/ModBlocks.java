package cadiboo.examplemod.init;

import cadiboo.examplemod.block.BlockModOre;
import cadiboo.examplemod.block.BlockResource;
import cadiboo.examplemod.util.ModReference;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Block Instances class<br/>
 * All the blocks in here will be public static final and null as their values will be filled by the magical @ObjectHolder
 *
 * @author Cadiboo
 */
@ObjectHolder(ModReference.MOD_ID)
public class ModBlocks {

	public static final BlockResource	EXAMPLE_BLOCK	= null;

	public static final BlockModOre		EXAMPLE_ORE		= null;

}
