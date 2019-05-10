package io.github.cadiboo.examplemod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Cadiboo
 */
@Mod(ExampleMod.MODID)
public final class ExampleMod {

	public static final String MODID = "examplemod";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public ExampleMod() {
		LOGGER.debug("Hello from Example Mod!");
	}

}
