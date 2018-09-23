package cadiboo.examplemod.util;

import java.util.Random;

import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.creativetab.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModUtil {

	/**
	 * Sets the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl#setRegistryName(net.minecraft.util.ResourceLocation) Registry Name} and the {@link net.minecraft.item.Item#setUnlocalizedName() Unlocalised Name} (if applicable) for the entry
	 *
	 * @param entry the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param name  the name for the entry that the registry name is derived from
	 */
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(final T entry, final String name) {
		return setRegistryNames(entry, new ResourceLocation(ModReference.MOD_ID, name));
	}

	/**
	 * Sets the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl#setRegistryName(net.minecraft.util.ResourceLocation) Registry Name} and the {@link net.minecraft.item.Item#setUnlocalizedName() Unlocalised Name} (if applicable) for the entry
	 *
	 * @param entry        the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param registryName the registry name for the entry that the unlocalised name is also gotten from
	 */
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(final T entry, final ResourceLocation registryName) {
		return setRegistryNames(entry, registryName, registryName.getResourcePath());
	}

	/**
	 * Sets the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl#setRegistryName(net.minecraft.util.ResourceLocation) Registry Name} and the {@link net.minecraft.item.Item#setUnlocalizedName() Unlocalised Name} (if applicable) for the entry
	 *
	 * @param entry           the {@link net.minecraftforge.registries.IForgeRegistryEntry.Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param registryName    the registry name for the entry
	 * @param unlocalizedName the unlocalized name for the entry
	 */
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(final T entry, final ResourceLocation registryName, final String unlocalizedName) {
		entry.setRegistryName(registryName);
		if (entry instanceof Block) {
			((Block) entry).setUnlocalizedName(unlocalizedName);
			setCreativeTab((Block) entry);
		}
		if (entry instanceof Item) {
			((Item) entry).setUnlocalizedName(unlocalizedName);
			setCreativeTab((Item) entry);
		}
		return entry;
	}

	/**
	 * Utility method to make sure that all our items appear on our creative tab
	 *
	 * @param item the {@link net.minecraft.item.Item Item}
	 */
	public static void setCreativeTab(final Item item) {
		if (item.getCreativeTab() == null) {
			item.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		}
	}

	/**
	 * Utility method to make sure that all our blocks appear on our creative tab
	 *
	 * @param block the {@link net.minecraft.block.Block Block}
	 */
	public static void setCreativeTab(final Block block) {
		if (block.getCreativeTabToDisplayOn() == null) {
			block.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		}
	}

	public static Block getBlockFromPos(final World worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock();
	}

	public static IBlockState getStateFromPos(final World worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos);
	}

	public static double randomBetween(final int min, final int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	/**
	 * Maps a value from one range to another range. Taken from https://stackoverflow.com/a/5732117
	 *
	 * @param input_start  the start of the input's range
	 * @param input_end    the end of the input's range
	 * @param output_start the start of the output's range
	 * @param output_end   the end of the output's range
	 * @param input        the input
	 * @return the newly mapped value
	 */
	public static double map(final double input_start, final double input_end, final double output_start, final double output_end, final double input) {
		final double input_range = input_end - input_start;
		final double output_range = output_end - output_start;

		return (((input - input_start) * output_range) / input_range) + output_start;
	}

	public static Side getLogicalSide(final World world) {
		if (world.isRemote) {
			return Side.CLIENT;
		} else {
			return Side.SERVER;
		}
	}

	public static void logLogicalSide(final World world) {
		ExampleMod.info("Logical Side: " + getLogicalSide(world));
	}

	/**
	 * Turns a class's name into a registry name<br>
	 * It expects the Class's Name to be in CamelCase format<br>
	 * It returns the registry name in snake_case format<br>
	 * <br>
	 * Examples:<br>
	 * (TileEntitySuperAdvancedFurnace, "TileEntity") -> super_advanced_furnace<br>
	 * (EntityPortableGenerator, "Entity") -> portable_generator<br>
	 * (TileEntityPortableGenerator, "Entity") -> tile_portable_generator<br>
	 * (EntityPortableEntityGeneratorEntity, "Entity") -> portable_generator<br>
	 *
	 * @param clazz      the class
	 * @param removeType the string to be removed from the class's name
	 * @return the recommended registry name for the class
	 */
	public static String getRegistryNameForClass(final Class clazz, final String removeType) {
		return org.apache.commons.lang3.StringUtils.uncapitalize(clazz.getSimpleName().replace(removeType, "")).replaceAll("([A-Z])", "_$1").toLowerCase();
	}

}
