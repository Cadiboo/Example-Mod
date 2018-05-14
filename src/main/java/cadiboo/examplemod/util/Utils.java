package cadiboo.examplemod.util;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.energy.IEnergyStorage;

public class Utils {

	public static float getEnergyFraction(IEnergyStorage storage) {
		if (storage == null)
			return 0;

		return (float) storage.getEnergyStored() / (float) storage.getMaxEnergyStored();
	}

	public static int getEnergyPercentage(IEnergyStorage storage) {
		return Math.round(getEnergyFraction(storage) * 100f);
	}

	public static Block getBlockFromPos(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock();
	}

	public static IBlockState getStateFromPos(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos);
	}

	public static double randomBetween(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public static TextureAtlasSprite getSpriteFromItemStack(ItemStack stack) {
		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		if (model == null)
			return null;
		List<BakedQuad> quads = model.getQuads(null, null, 0L);
		if (quads == null || quads.size() <= 0)
			return null;
		TextureAtlasSprite sprite = quads.get(0).getSprite();
		if (sprite == null)
			return null;
		return sprite;
	}

}
