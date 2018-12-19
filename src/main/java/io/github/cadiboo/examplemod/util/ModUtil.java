package io.github.cadiboo.examplemod.util;

import com.google.common.base.Preconditions;
import io.github.cadiboo.examplemod.creativetab.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.PrimitiveIterator;
import java.util.Random;

/**
 * Util that is used on BOTH physical sides
 *
 * @author Cadiboo
 */
@SuppressWarnings("WeakerAccess")
public final class ModUtil {

	private static final Random RANDOM = new Random();

	/**
	 * Sets the {@link Impl#setRegistryName(ResourceLocation) Registry Name} and the {@link Item#setTranslationKey(String) Translation Key} (if applicable) for the entry
	 *
	 * @param entry the {@link Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param name  the name for the entry that the registry name is derived from
	 * @return the entry
	 */
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final String name) {
		return setRegistryNames(entry, new ResourceLocation(ModReference.MOD_ID, name));
	}

	/**
	 * Sets the {@link Impl#setRegistryName(ResourceLocation) Registry Name} and the {@link Item#setTranslationKey(String) Translation Key} (if applicable) for the entry
	 *
	 * @param entry        the {@link Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param registryName the registry name for the entry that the translation key is also derived from
	 * @return the entry
	 */
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
		return setRegistryNames(entry, registryName, registryName.getPath());
	}

	/**
	 * Sets the {@link Impl#setRegistryName(ResourceLocation) Registry Name} and the {@link Item#setTranslationKey(String) Translation Key} (if applicable) for the entry
	 *
	 * @param entry           the {@link Impl IForgeRegistryEntry.Impl<?>} to set the names for
	 * @param registryName    the registry name for the entry
	 * @param translationKey the unlocalized name for the entry
	 * @return the entry
	 */
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName, @Nonnull final String translationKey) {
		entry.setRegistryName(registryName);
		if (entry instanceof Block) {
			((Block) entry).setTranslationKey(translationKey);
			setCreativeTab((Block) entry);
		}
		if (entry instanceof Item) {
			((Item) entry).setTranslationKey(translationKey);
			setCreativeTab((Item) entry);
		}
		return entry;
	}

	/**
	 * Utility method to make sure that all our items appear on our creative tab, the search tab and any other tab they specify
	 *
	 * @param item the {@link Item Item}
	 * @return an array of all tabs that this item is on.
	 */
	@Nonnull
	public static CreativeTabs[] getCreativeTabs(@Nonnull final Item item) {
		Preconditions.checkNotNull(item, "Item cannot be null!");
		if (item.getRegistryName().getNamespace().equals(ModReference.MOD_ID)) {
			return new CreativeTabs[]{item.getCreativeTab(), ModCreativeTabs.CREATIVE_TAB, CreativeTabs.SEARCH};
		}
		return new CreativeTabs[]{item.getCreativeTab(), CreativeTabs.SEARCH};
	}

	/**
	 * Utility method to make sure that all our items appear on our creative tab
	 *
	 * @param item the {@link Item Item}
	 */
	public static void setCreativeTab(@Nonnull final Item item) {
		if (item.getCreativeTab() == null) {
			item.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		}
	}

	/**
	 * Utility method to make sure that all our blocks appear on our creative tab
	 *
	 * @param block the {@link Block Block}
	 */
	public static void setCreativeTab(@Nonnull final Block block) {
		if (block.getCreativeTab() == null) {
			block.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		}
	}

	/**
	 * Returns a random between the specified values;
	 * @param min the minimum value of the random number
	 * @param max the maximum value of the random number
	 * @return the random number
	 */
	public static double randomBetween(final int min, final int max) {
		return RANDOM.nextInt((max - min) + 1) + min;
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

	@Nonnull
	public static Side getLogicalSide(@Nonnull final World world) {
		if (world.isRemote) {
			return Side.CLIENT;
		} else {
			return Side.SERVER;
		}
	}

	public static void logLogicalSide(@Nonnull final Logger logger, @Nonnull final World world) {
		logger.info("Logical Side: " + getLogicalSide(world));
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
	@Nonnull
	public static String getRegistryNameForClass(@Nonnull final Class<?> clazz, @Nonnull final String removeType) {
		return StringUtils.uncapitalize(clazz.getSimpleName().replace(removeType, "")).replaceAll("([A-Z])", "_$1").toLowerCase();
	}

	/**
	 * Logs all {@link Field Field}s and their values of an object with the {@link Level#INFO INFO} level.
	 *
	 * @param logger  the logger to dump on
	 * @param objects the objects to dump.
	 */
	public static void dump(@Nonnull final Logger logger, @Nonnull final Object... objects) {
		for (final Object object : objects) {
			final Field[] fields = object.getClass().getDeclaredFields();
			logger.info("Dump of " + object + ":");
			for (final Field field : fields) {
				try {
					field.setAccessible(true);
					logger.info(field.getName() + " - " + field.get(object));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.info("Error getting field " + field.getName());
					logger.info(e.getLocalizedMessage());
				}
			}
		}
	}

}
