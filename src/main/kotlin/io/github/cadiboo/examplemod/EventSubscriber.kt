package io.github.cadiboo.examplemod

import com.google.common.base.Preconditions
import io.github.cadiboo.examplemod.block.BlockExampleTileEntity
import io.github.cadiboo.examplemod.block.BlockModOre
import io.github.cadiboo.examplemod.block.BlockResource
import io.github.cadiboo.examplemod.init.ModBlocks
import io.github.cadiboo.examplemod.item.ItemExampleIngot
import io.github.cadiboo.examplemod.tileentity.TileEntityExampleTileEntity
import io.github.cadiboo.examplemod.util.ModReference
import io.github.cadiboo.examplemod.util.ModUtil
import net.minecraft.block.Block
import net.minecraft.crash.CrashReport
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ReportedException
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.EntityEntry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.registries.IForgeRegistry
import java.util.Arrays

/**
 * Subscribe to events that should be handled on both PHYSICAL sides in this class
 */
@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
object EventSubscriber {

    private val entityId = 0

    /* register blocks */
    @SubscribeEvent
    fun onRegisterBlocksEvent(@Nonnull event: RegistryEvent.Register<Block>) {
        val registry = event.getRegistry()

        registry.register(BlockResource("example_block"))
        registry.register(BlockModOre("example_ore"))
        registry.register(BlockExampleTileEntity("example_tile_entity"))

        ExampleMod.EXAMPLE_MOD_LOG.debug("Registered blocks")

        registerTileEntities()

        ExampleMod.EXAMPLE_MOD_LOG.debug("Registered tile entities")

    }

    private fun registerTileEntities() {
        registerTileEntity(TileEntityExampleTileEntity::class, "example_tile_entity")
    }

    private fun registerTileEntity(@Nonnull clazz: Class<out TileEntity>, name: String) {
        try {
            GameRegistry.registerTileEntity(clazz, ResourceLocation(ModReference.MOD_ID, name))
        } catch (exception: Exception) {
            val crashReport = CrashReport("Error registering Tile Entity " + clazz.getSimpleName(), exception)
            crashReport.makeCategory("Registering Tile Entity")
            throw ReportedException(crashReport)
        }

    }

    /* register items */
    @SubscribeEvent
    fun onRegisterItemsEvent(@Nonnull event: RegistryEvent.Register<Item>) {
        val registry = event.getRegistry()

        // item blocks
        // make an array of all the blocks we want to have items
        Arrays.stream(arrayOf<Block>(

                ModBlocks.EXAMPLE_BLOCK, ModBlocks.EXAMPLE_ORE, ModBlocks.EXAMPLE_TILE_ENTITY)).forEach({ block ->
            Preconditions.checkNotNull(block.getRegistryName(), "Registry name cannot be null!")
            registry.register(
                    ModUtil.setCreativeTab( // set it's creative tab to our creativetab (Optional)
                            ModUtil.setRegistryNames( //set its name
                                    ItemBlock(block), //make the itemblock
                                    block.getRegistryName())
                    )
            )
        })

        // items
        // you can also instantiate items like this, however its not often used for a number of reasons
        registry.register(ModUtil.setCreativeTab(ModUtil.setRegistryNames(Item(), "example_item")))
        registry.register(ItemExampleIngot("example_ingot"))

        ExampleMod.EXAMPLE_MOD_LOG.debug("Registered items")

    }

    /* register entities */
    @SubscribeEvent
    fun onRegisterEntitiesEvent(@Nonnull event: RegistryEvent.Register<EntityEntry>) {
        val registry = event.getRegistry()

        val clazz = Entity____.class
        val registryName = new ResourceLocation (MOD_ID, ModUtil.getRegistryNameForClass(clazz, "Entity"))
        registry.register(
                EntityEntryBuilder.create()
                        .entity(clazz)
                        .id(registryName, entityId++)
                        .name(registryName.getPath())
                        .tracker(range, updateFrequency, sendVelocityUpdates)
                        .egg(primaryColor, secondaryColor)
                        .build()
        )

        ExampleMod.EXAMPLE_MOD_LOG.debug("Registered entities")

    }

}
