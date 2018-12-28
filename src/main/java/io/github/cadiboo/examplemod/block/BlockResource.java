package io.github.cadiboo.examplemod.block;

import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

/**
 * The same as a block of Iron or a block of Gold but for our stuff
 *
 * @author Cadiboo
 */
public class BlockResource extends Block {

	public BlockResource(@Nonnull final String name) {
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setHardness(1);
	}

}
