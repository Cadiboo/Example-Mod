package cadiboo.examplemod.handler.network;

import java.io.IOException;

import cadiboo.examplemod.tileentity.TileEntityBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncTileEntity implements IMessage, IMessageHandler<PacketSyncTileEntity, IMessage> {

	private NBTTagCompound	syncTag;
	private BlockPos		pos;

	public PacketSyncTileEntity() {
	}

	public PacketSyncTileEntity(NBTTagCompound syncTag, BlockPos pos) {
		this.syncTag = syncTag;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.syncTag = new PacketBuffer(buf).readCompoundTag();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		new PacketBuffer(buf).writeCompoundTag(this.syncTag);
		buf.writeLong(this.pos.toLong());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketSyncTileEntity message, MessageContext ctx) {
		// WIPTech.logger.info("message: " + message);
		if (message.syncTag != null) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(message.pos);
				if (tile != null && tile instanceof TileEntityBase) {
					// WIPTech.logger.info("syncTag = " + message.syncTag);
					((TileEntityBase) tile).readNBT(message.syncTag, TileEntityBase.NBTType.SYNC);
					((TileEntityBase) tile).onSyncPacket();
				}
			});
		}
		return null;
	}

}