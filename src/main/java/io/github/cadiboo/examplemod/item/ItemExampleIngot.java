package io.github.cadiboo.examplemod.item;

import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.item.Item;

/**
 * The same as an Iron Ingot or a Gold Ingot but for our stuff
 *
 * @author Cadiboo
 */
public class ItemExampleIngot extends Item implements IModItem {

	public ItemExampleIngot(String name) {
		ModUtil.setRegistryNames(this, name);
	}

}
