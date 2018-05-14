package cadiboo.examplemod.client;

import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.init.ModBlocks;
import cadiboo.examplemod.init.ModItems;
import cadiboo.examplemod.util.IProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Proxy implements IProxy {

	public static final ItemStack TAB_ICON_STACK = new ItemStack(ModItems.EXAMPLE_ITEM);

	@Override
	public void logLogicalSide() {
		ExampleMod.info("Logical Side: Client");
	}

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	@Override
	public String getSide() {
		return "Client";
	}

}
