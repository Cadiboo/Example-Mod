package cadiboo.examplemod.creativetab;

import cadiboo.examplemod.init.ModItems;
import cadiboo.examplemod.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTab extends CreativeTabs {
	
	public static final ModCreativeTab TAB = new ModCreativeTab();
	
	public ModCreativeTab() {
		super(Reference.ID);
		setBackgroundImageName("item_search.png");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.EXAMPLE_ITEM);
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}

}
