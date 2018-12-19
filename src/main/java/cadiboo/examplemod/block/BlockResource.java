package cadiboo.examplemod.block;

import cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * The same as a block of Iron or a block of Gold but for our stuff
 *
 * @author Cadiboo
 */
public class BlockResource extends Block implements IModBlock {

	public BlockResource(final String name) {
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setHardness(1);
	}

}
