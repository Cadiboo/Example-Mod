package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.block.ElectricFurnaceBlock;
import io.github.cadiboo.examplemod.block.HeatCollectorBlock;
import io.github.cadiboo.examplemod.block.MiniModelBlock;
import io.github.cadiboo.examplemod.block.ModFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link Block}s.
 * Suppliers that create Blocks are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the Block Registry Event is fired by Forge and it is time for the mod to
 * register its Blocks, our Blocks are created and registered by the DeferredRegister.
 * The Block Registry Event will always be called before the Item registry is filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ExampleMod.MODID);

	// This block has the ROCK material, meaning it needs at least a wooden pickaxe to break it. It is very similar to Iron Ore
	public static final RegistryObject<Block> EXAMPLE_ORE = BLOCKS.register("example_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)));
	// This block has the IRON material, meaning it needs at least a stone pickaxe to break it. It is very similar to the Iron Block
	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)));
	// This block has the MISCELLANEOUS material. It is very similar to the Redstone Torch
	public static final RegistryObject<Block> MINI_MODEL = BLOCKS.register("mini_model", () -> new MiniModelBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.5F).lightValue(13).doesNotBlockMovement()));
	// This block has the ROCK material, meaning it needs at least a wooden pickaxe to break it. It is very similar to the Furnace
	public static final RegistryObject<Block> HEAT_COLLECTOR = BLOCKS.register("heat_collector", () -> new HeatCollectorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)));
	public static final RegistryObject<Block> ELECTRIC_FURNACE = BLOCKS.register("electric_furnace", () -> new ElectricFurnaceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F)));
	public static final RegistryObject<Block> MOD_FURNACE = BLOCKS.register("mod_furnace", () -> new ModFurnaceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)));

}
