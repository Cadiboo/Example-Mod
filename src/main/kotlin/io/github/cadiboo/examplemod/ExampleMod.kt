package io.github.cadiboo.examplemod

import io.github.cadiboo.examplemod.network.ModNetworkManager
import io.github.cadiboo.examplemod.util.IProxy
import io.github.cadiboo.examplemod.util.ModGuiHandler
import io.github.cadiboo.examplemod.world.gen.ModWorldGenerator
import net.minecraftforge.common.ForgeModContainer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.Mod.Instance
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import io.github.cadiboo.examplemod.util.ModReference.ACCEPTED_VERSIONS
import io.github.cadiboo.examplemod.util.ModReference.CERTIFICATE_FINGERPRINT
import io.github.cadiboo.examplemod.util.ModReference.CLIENT_PROXY_CLASS
import io.github.cadiboo.examplemod.util.ModReference.DEPENDENCIES
import io.github.cadiboo.examplemod.util.ModReference.MOD_ID
import io.github.cadiboo.examplemod.util.ModReference.MOD_NAME
import io.github.cadiboo.examplemod.util.ModReference.SERVER_PROXY_CLASS
import io.github.cadiboo.examplemod.util.ModReference.VERSION

/**
 * Our main mod class
 *
 * @author Cadiboo
 */
@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS, dependencies = DEPENDENCIES, certificateFingerprint = CERTIFICATE_FINGERPRINT)
class ExampleMod {

    val EXAMPLE_MOD_LOG = LogManager.getLogger(MOD_ID)

    private val LOGGER = LogManager.getLogger()

    @Instance(MOD_ID)
    var instance: ExampleMod? = null

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    var proxy: IProxy? = null

    /**
     * Run before anything else. ~~Read your config, create blocks, items, etc, and register them with the GameRegistry~~
     *
     * @param event the event
     * @see ForgeModContainer.preInit
     */
    @EventHandler
    fun onPreInit(event: FMLPreInitializationEvent) {
        LOGGER.debug("preInit")
        proxy!!.logPhysicalSide(EXAMPLE_MOD_LOG)

        GameRegistry.registerWorldGenerator(ModWorldGenerator(), 3)
        ModNetworkManager()
        NetworkRegistry.INSTANCE.registerGuiHandler(this, ModGuiHandler())

        // register Capabilities if you have any

    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes, send FMLInterModComms messages to other mods.
     *
     * @param event the event
     */
    @EventHandler
    fun onInit(event: FMLInitializationEvent) {
        LOGGER.debug("init")
    }

    /**
     * Mod compatibility, or anything which depends on other mods’ init phases being finished.
     *
     * @param event the event
     * @see ForgeModContainer.postInit
     */
    @EventHandler
    fun onPostInit(event: FMLPostInitializationEvent) {
        LOGGER.debug("postInit")
    }

    /**
     * Do your invalid fingerprint handling here
     *
     * @param event the event
     * @see "https://tutorials.darkhax.net/tutorials/jar_signing/" and "https://mcforge.readthedocs.io/en/latest/concepts/jarsigning/"
     */
    @EventHandler
    fun onFingerprintViolation(event: FMLFingerprintViolationEvent) {
        EXAMPLE_MOD_LOG.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!")
    }


}
