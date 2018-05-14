package cadiboo.examplemod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import cadiboo.examplemod.block.BlockBase;
import cadiboo.examplemod.block.BlockOre;
import cadiboo.examplemod.block.BlockResourceBlock;
import cadiboo.examplemod.handler.EnumHandler.Ores;
import cadiboo.examplemod.handler.EnumHandler.ResourceBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final BlockOre	EXAMPLE_ORE = new BlockOre("example_ore", Material.ROCK, Ores.EXAMPLE);
	
	public static final BlockResourceBlock	EXAMPLE_BLOCK = new BlockResourceBlock("example_block", Material.IRON, ResourceBlocks.EXAMPLE);
	
	public static final Block[] BLOCKS = {

			EXAMPLE_ORE, EXAMPLE_BLOCK,
			
	};

	public static final Set<Block> HIDDEN_BLOCKS = Sets.newHashSet();

	public static Set<Block> getHiddenBlocks() {
		for (int i = 0; i < BLOCKS.length; i++) {
			if (BLOCKS[i] instanceof BlockBase && ((BlockBase) BLOCKS[i]).isHiddenBlock())
				HIDDEN_BLOCKS.add(BLOCKS[i]);
		}
		return HIDDEN_BLOCKS;
	}

	public static List<Block> getOres() {
		List<Block> OreBlocks = new ArrayList<Block>();
		for (int i = 0; i < BLOCKS.length; i++) {
			if (BLOCKS[i] instanceof BlockOre)
				OreBlocks.add(BLOCKS[i]);
		}
		return OreBlocks;
	}

	public static final Set<Block> TILE_ENTITIES = Sets.newHashSet();

	public static Set<Block> getTileEntities() {
		for (int i = 0; i < BLOCKS.length; i++) {
			if (BLOCKS[i] instanceof BlockBase && ((BlockBase) BLOCKS[i]).isTileEntity())
				HIDDEN_BLOCKS.add(BLOCKS[i]);
		}
		return HIDDEN_BLOCKS;
	}

}
