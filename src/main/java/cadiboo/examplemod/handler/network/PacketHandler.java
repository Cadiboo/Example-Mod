package cadiboo.examplemod.handler.network;

import cadiboo.examplemod.util.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	private static final String					CHANNEL	= Reference.ID + "_chanel";	// CANT BE LONGER THAN 20 CHARS
	public static final SimpleNetworkWrapper	NETWORK	= NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);

	public PacketHandler() {
		int networkIds = 0;
		NETWORK.registerMessage(PacketSyncTileEntity.class, PacketSyncTileEntity.class, networkIds++, Side.CLIENT);
	}
}