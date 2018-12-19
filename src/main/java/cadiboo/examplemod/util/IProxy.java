package cadiboo.examplemod.util;

import cadiboo.examplemod.ExampleMod;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

/**
 * Some basic functions that differ depending on the physical side
 *
 * @author Cadiboo
 */
public interface IProxy {

	String localize(String unlocalized);

	String localizeAndFormat(String unlocalized, Object... args);

	default void logPhysicalSide(Logger logger) {
		logger.info("Physical Side: " + getPhysicalSide());
	}

	Side getPhysicalSide();
}