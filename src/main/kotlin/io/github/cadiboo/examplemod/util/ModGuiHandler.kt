package io.github.cadiboo.examplemod.util

import net.minecraft.client.gui.Gui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Handles the opening (creation) of Guis for both the server and client
 *
 * @author Cadiboo
 */
class ModGuiHandler : IGuiHandler {

    const val EXAMPLE_GUI_1 = 0
    const val EXAMPLE_GUI_2 = 1
    const val EXAMPLE_GUI_3 = 2

    /**
     * gets the server's part of a Gui
     *
     * @return a [Container] for the server
     */
    @Override
    fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {
        return when (ID) {
            EXAMPLE_GUI_1 ->
                // get container for gui
                null
            EXAMPLE_GUI_2 ->
                // get container for gui
                null
            EXAMPLE_GUI_3 ->
                // get container for gui
                null
            else -> null
        }
    }

    /**
     * gets the client's part of a Gui
     *
     * @return a [GuiScreen] for the client
     */
    @Override
    @SideOnly(Side.CLIENT)
    fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Gui? {
        return when (ID) {
            EXAMPLE_GUI_1 ->
                // get gui for gui
                null
            EXAMPLE_GUI_2 ->
                // get gui for gui
                null
            EXAMPLE_GUI_3 ->
                // get gui for gui
                null
            else -> null
        }
    }


}
