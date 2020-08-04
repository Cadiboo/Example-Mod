package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.item.ModdedSpawnEggItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link Item}s.
 * Suppliers that create Items are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the Item Registry Event is fired by Forge and it is time for the mod to
 * register its Items, our Items are created and registered by the DeferredRegister.
 * The Item Registry Event will always be called after the Block registry is filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModItems {

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ExampleMod.MODID);

	// This is a very simple Item. It has no special properties except for being on our creative tab.
	public static final RegistryObject<Item> EXAMPLE_CRYSTAL = ITEMS.register("example_crystal", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	// This is another very simple Item. It also has no special properties except for being on our creative tab.
	public static final RegistryObject<Item> EXAMPLE_CRYSTAL_SHARD = ITEMS.register("example_crystal_shard", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	// This is the spawn egg for our Wild Boar.
	public static final RegistryObject<ModdedSpawnEggItem> WILD_BOAR_SPAWN_EGG = ITEMS.register("wild_boar_spawn_egg", () -> new ModdedSpawnEggItem(ModEntityTypes.WILD_BOAR, 0xF0A5A2, 0xA9672B, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	// This is a sword made out of our crystals.
	public static final RegistryObject<SwordItem> EXAMPLE_SWORD = ITEMS.register("example_sword", () -> new SwordItem(ModItemTiers.CRYSTAL, 3, -2.4F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	// This is an axe made out of our crystals.
	public static final RegistryObject<AxeItem> EXAMPLE_AXE = ITEMS.register("example_axe", () -> new AxeItem(ModItemTiers.CRYSTAL, 6.0F, -3.0F, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

}
