package cadiboo.examplemod.item;

import cadiboo.examplemod.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item {

	public ItemBase(String name) {
		super();
		this.setRegistryName(Reference.ID, name);
		this.setUnlocalizedName(name);
	}

	// Beacon Payment
	private ItemBase beaconPayment;

	public ItemBase setBeaconPayment() {
		return beaconPayment = this;
	}

	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return this == beaconPayment;
	}
}