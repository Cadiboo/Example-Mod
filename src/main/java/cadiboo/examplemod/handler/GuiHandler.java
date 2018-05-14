package cadiboo.examplemod.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int	TILE_ENTITY1_NAME			= 0;
	public static final int	TILE_ENTITY2_NAME			= 1;

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
//			case TILE_ENTITY1_NAME:
//				return new ContainerTileEntity1(params);
//			case TILE_ENTITY2_NAME:
//				return new ContainerTileEntity2(params);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
//			case TILE_ENTITY1_NAME:
//				return new GuiTileEntity1(params);
//			case TILE_ENTITY2_NAME:
//				return new GuiTileEntity2(params);
			default:
				return null;
		}
	}

}
