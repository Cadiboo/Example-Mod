package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

/**
 * The same as Iron Ore or Gold Ore but for our stuff
 *
 * @author Cadiboo
 */
public class BlockModOre extends Block implements IModBlock {

	public BlockModOre(@Nonnull final String name) {
		super(Material.ROCK);
		ModUtil.setRegistryNames(this, name);
		this.setHardness(1);
	}

}
