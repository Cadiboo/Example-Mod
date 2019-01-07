package io.github.cadiboo.examplemod.util

import com.google.common.base.Preconditions
import io.github.cadiboo.examplemod.creativetab.ModCreativeTabs
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.IForgeRegistryEntry.Impl
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger
import java.lang.reflect.Field
import java.util.Random

/**
 * Util that is used on BOTH physical sides
 *
 * @author Cadiboo
 */
@SuppressWarnings("WeakerAccess")
object ModUtil {

    private val RANDOM = Random()

    /**
     * Sets the [Registry Name][Impl.setRegistryName] and the [Translation Key][Item.setTranslationKey] (if applicable) for the entry
     *
     * @param entry the [Impl] to set the names for
     * @param name  the name for the entry that the registry name is derived from
     * @return the entry
     */
    @Nonnull
    fun <T : IForgeRegistryEntry.Impl<*>> setRegistryNames(@Nonnull entry: T, @Nonnull name: String): T {
        return setRegistryNames(entry, ResourceLocation(ModReference.MOD_ID, name))
    }

    /**
     * Sets the [Registry Name][Impl.setRegistryName] and the [Translation Key][Item.setTranslationKey] (if applicable) for the entry
     *
     * @param entry        the [Impl] to set the names for
     * @param registryName the registry name for the entry that the translation key is also derived from
     * @return the entry
     */
    @Nonnull
    fun <T : IForgeRegistryEntry.Impl<*>> setRegistryNames(@Nonnull entry: T, @Nonnull registryName: ResourceLocation): T {
        return setRegistryNames(entry, registryName, registryName.getPath())
    }

    /**
     * Sets the [Registry Name][Impl.setRegistryName] and the [Translation Key][Item.setTranslationKey] (if applicable) for the entry
     *
     * @param entry          the [Impl] to set the names for
     * @param registryName   the registry name for the entry
     * @param translationKey the translation key for the entry
     * @return the entry
     */
    @Nonnull
    fun <T : IForgeRegistryEntry.Impl<*>> setRegistryNames(@Nonnull entry: T, @Nonnull registryName: ResourceLocation, @Nonnull translationKey: String): T {
        entry.setRegistryName(registryName)
        if (entry is Block) {
            (entry as Block).setTranslationKey(translationKey)
        }
        if (entry is Item) {
            (entry as Item).setTranslationKey(translationKey)
        }
        return entry
    }

    @Nonnull
    fun <T : IForgeRegistryEntry.Impl<*>> setCreativeTab(@Nonnull entry: T): T {
        return setCreativeTab(entry, ModCreativeTabs.CREATIVE_TAB)
    }

    @Nonnull
    fun <T : IForgeRegistryEntry.Impl<*>> setCreativeTab(@Nonnull entry: T, creativeTab: CreativeTabs): T {
        if (entry is Block) {
            (entry as Block).setCreativeTab(creativeTab)
        }
        if (entry is Item) {
            (entry as Item).setCreativeTab(creativeTab)
        }
        return entry
    }

    /**
     * Utility method to make sure that all our items appear on our creative tab, the search tab and any other tab they specify
     *
     * @param item the [Item]
     * @return an array of all tabs that this item is on.
     */
    @Nonnull
    fun getCreativeTabs(@Nonnull item: Item): Array<CreativeTabs> {
        Preconditions.checkNotNull(item, "Item cannot be null!")
        return if (item.getRegistryName().getNamespace().equals(ModReference.MOD_ID)) {
            arrayOf<CreativeTabs>(item.getCreativeTab(), ModCreativeTabs.CREATIVE_TAB, CreativeTabs.SEARCH)
        } else arrayOf<CreativeTabs>(item.getCreativeTab(), CreativeTabs.SEARCH)
    }

    /**
     * Returns a random between the specified values;
     *
     * @param min the minimum value of the random number
     * @param max the maximum value of the random number
     * @return the random number
     */
    fun randomBetween(min: Int, max: Int): Double {
        return RANDOM.nextInt(max - min + 1) + min
    }

    /**
     * Maps a value from one range to another range. Taken from https://stackoverflow.com/a/5732117
     *
     * @param input       the input
     * @param inputStart  the start of the input's range
     * @param inputEnd    the end of the input's range
     * @param outputStart the start of the output's range
     * @param outputEnd   the end of the output's range
     * @return the newly mapped value
     */
    fun map(input: Double, inputStart: Double, inputEnd: Double, outputStart: Double, outputEnd: Double): Double {
        val input_range = inputEnd - inputStart
        val output_range = outputEnd - outputStart

        return (input - inputStart) * output_range / input_range + outputStart
    }

    @Nonnull
    fun getLogicalSide(@Nonnull world: World): Side {
        return if (world.isRemote) {
            Side.CLIENT
        } else {
            Side.SERVER
        }
    }

    fun logLogicalSide(@Nonnull logger: Logger, @Nonnull world: World) {
        logger.info("Logical Side: " + getLogicalSide(world))
    }

    /**
     * Logs all [Field]s and their values of an object with the [INFO][Level.INFO] level.
     *
     * @param logger  the logger to dump on
     * @param objects the objects to dump.
     */
    fun dump(@Nonnull logger: Logger, @Nonnull vararg objects: Object) {
        for (`object` in objects) {
            val fields = `object`.getClass().getDeclaredFields()
            logger.info("Dump of $`object`:")
            for (field in fields) {
                try {
                    field.setAccessible(true)
                    logger.info(field.getName() + " - " + field.get(`object`))
                } catch (e: IllegalArgumentException) {
                    logger.info("Error getting field " + field.getName())
                    logger.info(e.getLocalizedMessage())
                } catch (e: IllegalAccessException) {
                    logger.info("Error getting field " + field.getName())
                    logger.info(e.getLocalizedMessage())
                }

            }
        }
    }

}
