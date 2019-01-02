package io.github.cadiboo.examplemod.item;

import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * The same as an Iron Ingot or a Gold Ingot but for our stuff
 *
 * @author Cadiboo
 */
public class ItemExampleIngot extends Item {

	public ItemExampleIngot(@Nonnull String name) {
		ModUtil.setRegistryNames(this, name);
		ModUtil.setCreativeTab(this);
	}

}
