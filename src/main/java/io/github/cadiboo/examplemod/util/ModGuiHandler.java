package io.github.cadiboo.examplemod.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Handles the opening (creation) of Guis for both the server and client
 *
 * @author Cadiboo
 */
public class ModGuiHandler implements IGuiHandler {

	public static final int EXAMPLE_GUI_1 = 0;
	public static final int EXAMPLE_GUI_2 = 1;
	public static final int EXAMPLE_GUI_3 = 2;

	/**
	 * gets the server's part of a Gui
	 *
	 * @return a {@link net.minecraft.inventory.Container Container} for the server
	 */
	@Override
	public Container getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		switch (ID) {
			case EXAMPLE_GUI_1:
				// get container for gui
				return null;
			case EXAMPLE_GUI_2:
				// get container for gui
				return null;
			case EXAMPLE_GUI_3:
				// get container for gui
				return null;
			default:
				return null;
		}
	}

	/**
	 * gets the client's part of a Gui
	 *
	 * @return a {@link net.minecraft.client.gui.GuiScreen GuiScreen} for the client
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Gui getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		switch (ID) {
			case EXAMPLE_GUI_1:
				// get gui for gui
				return null;
			case EXAMPLE_GUI_2:
				// get gui for gui
				return null;
			case EXAMPLE_GUI_3:
				// get gui for gui
				return null;
			default:
				return null;
		}
	}

}
