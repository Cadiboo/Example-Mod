package io.github.cadiboo.examplemod.util

import net.minecraftforge.fml.relauncher.Side
import org.apache.logging.log4j.Logger

/**
 * Some basic functions that differ depending on the physical side
 *
 * @author Cadiboo
 */
interface IProxy {

    abstract fun localize(unlocalized: String): String

    abstract fun localizeAndFormat(unlocalized: String, vararg args: Any): String

    fun logPhysicalSide(logger: Logger) {
        logger.debug("Physical Side: " + getPhysicalSide())
    }

    abstract fun getPhysicalSide(): Side

}
