package cadiboo.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cadiboo.examplemod.handler.GuiHandler;
import cadiboo.examplemod.handler.network.PacketHandler;
import cadiboo.examplemod.util.IProxy;
import cadiboo.examplemod.util.Reference;
import cadiboo.examplemod.world.WorldGen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class ExampleMod {

	@Instance(Reference.ID)
	public static ExampleMod instance;

	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static IProxy proxy;

	public static final Logger logger = LogManager.getLogger(Reference.ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ExampleMod.proxy.logLogicalSide();

		new PacketHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new WorldGen(), 3);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		// Mod compatibility, or anything which depends on other mods’ init phases being
		// finished.
	}

	public static void info(Object... msgs) {
		for (Object msg : msgs) {
			logger.info(msg);
		}
	}

	public static void error(Object... msgs) {
		for (Object msg : msgs) {
			logger.error(msg);
		}
	}

}