package cadiboo.examplemod.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Util that is only used on the client i.e. Rendering code
 *
 * @author Cadiboo
 */
@SideOnly(Side.CLIENT)
public final class ClientUtil {

	/**
	 * Rotation algorithm Taken off Max_the_Technomancer from <a href= "https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2772267-tesr-getting-darker-and-lighter-as-it-rotates">here</a>
	 *
	 * @param face the {@link net.minecraft.util.EnumFacing face} to rotate for
	 * @author Cadiboo
	 */
	public static void rotateForFace(final EnumFacing face) {
		GlStateManager.rotate(face == EnumFacing.DOWN ? 0 : face == EnumFacing.UP ? 180F : (face == EnumFacing.NORTH) || (face == EnumFacing.EAST) ? 90F : -90F, face.getAxis() == EnumFacing.Axis.Z ? 1 : 0, 0, face.getAxis() == EnumFacing.Axis.Z ? 0 : 1);
		GlStateManager.rotate(-90, 0, 0, 1);
	}

	/**
	 * All Light methods I can remember
	 *
	 * @author Cadiboo
	 */
	private static void allLightMethods() {

		// GlStateManager.disableLighting();
		// GlStateManager.enableLighting();
		//
		// RenderHelper.disableStandardItemLighting();
		// RenderHelper.enableStandardItemLighting();
		//
		// RenderHelper.enableGUIStandardItemLighting();
		//
		// net.minecraftforge.client.model.pipeline.LightUtil.diffuseLight(EnumFacing side);
		// net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(BufferBuilder wr, BakedQuad quad, int auxColor);
		//
		// Tessellator.getInstance().getBuffer().lightmap(p_187314_1_, p_187314_2_);
		//
		// OpenGlHelper.setLightmapTextureCoords(int target, float p_77475_1_, float t)

	}

	/**
	 * Put a lot of effort into this, it gets the entities exact (really, really exact) position
	 *
	 * @param entity       The entity to calculate the position of
	 * @param partialTicks The multiplier used to predict where the entity is/will be
	 * @return The position of the entity as a Vec3d
	 * @author Cadiboo
	 */
	public static Vec3d getEntityRenderPos(final Entity entity, final double partialTicks) {
		double flyingMultiplier = 1.825;
		double yFlying = 1.02;
		double yAdd = 0.0784000015258789;

		if ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isFlying) {
			flyingMultiplier = 1.1;
			yFlying = 1.67;
			yAdd = 0;
		}

		final double yGround = ((entity.motionY + yAdd) == 0) && (entity.prevPosY > entity.posY) ? entity.posY - entity.prevPosY : 0;
		double xFall = 1;
		if (flyingMultiplier == 1.825) {
			if (entity.motionX != 0) {
				if ((entity.motionY + yAdd) != 0) {
					xFall = 0.6;
				} else if (yGround != 0) {
					xFall = 0.6;
				}
			} else {
				xFall = 0.6;
			}
		}

		double zFall = 1;
		if (flyingMultiplier == 1.825) {
			if (entity.motionZ != 0) {
				if ((entity.motionY + yAdd) != 0) {
					zFall = 0.6;
				} else if (yGround != 0) {
					zFall = 0.6;
				}
			} else {
				zFall = 0.6;
			}
		}

		final double dX = entity.posX - ((entity.prevPosX - entity.posX) * partialTicks) - ((entity.motionX * xFall) * flyingMultiplier);
		final double dY = entity.posY - yGround - ((entity.prevPosY - entity.posY) * partialTicks) - ((entity.motionY + yAdd) * yFlying);
		final double dZ = entity.posZ - ((entity.prevPosZ - entity.posZ) * partialTicks) - ((entity.motionZ * zFall) * flyingMultiplier);

		return new Vec3d(dX, dY, dZ);
	}

	/**
	 * Rotates around X axis based on Pitch input and around Y axis based on Yaw input
	 *
	 * @param pitch
	 * @param yaw
	 */
	@SideOnly(Side.CLIENT)
	public static void rotateForPitchYaw(final double pitch, final double yaw) {
		GlStateManager.rotate((float) yaw, 0, 1, 0);
		GlStateManager.rotate((float) pitch, 1, 0, 0);
	}

	/**
	 * Gets the pitch rotation between two vectors
	 *
	 * @param source
	 * @param destination
	 * @return the pitch rotation
	 */
	@SideOnly(Side.CLIENT)
	public static double getPitch(final Vec3d source, final Vec3d destination) {
		double pitch = Math.atan2(destination.y, Math.sqrt((destination.x * destination.x) + (destination.z * destination.z)));
		pitch = pitch * (180 / Math.PI);
		pitch = pitch < 0 ? 360 - (-pitch) : pitch;
		return 90 - pitch;
	}

	/**
	 * Gets the yaw rotation between two vectors
	 *
	 * @param source
	 * @param destination
	 * @return the yaw rotation
	 */
	public static double getYaw(final Vec3d source, final Vec3d destination) {
		double yaw = Math.atan2(destination.x - source.x, destination.z - source.z);
		yaw = yaw * (180 / Math.PI);
		yaw = yaw < 0 ? 360 - (-yaw) : yaw;
		return yaw + 90;
	}

}
