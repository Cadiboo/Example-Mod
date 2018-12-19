package io.github.cadiboo.examplemod.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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
	 */
	public static void rotateForFace(final EnumFacing face) {
		GlStateManager.rotate(face == EnumFacing.DOWN ? 0 : face == EnumFacing.UP ? 180F : (face == EnumFacing.NORTH) || (face == EnumFacing.EAST) ? 90F : -90F, face.getAxis() == EnumFacing.Axis.Z ? 1 : 0, 0, face.getAxis() == EnumFacing.Axis.Z ? 0 : 1);
		GlStateManager.rotate(-90, 0, 0, 1);
	}

	/**
	 * Put a lot of effort into this, it gets the entities exact (really, really exact) position
	 *
	 * @param entity       The entity to calculate the position of
	 * @param partialTicks The multiplier used to predict where the entity is/will be
	 * @return The position of the entity as a Vec3d
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

	/**
	 * @param red   the red value of the color, between 0x00 (decimal 0) and 0xFF (decimal 255)
	 * @param green the red value of the color, between 0x00 (decimal 0) and 0xFF (decimal 255)
	 * @param blue  the red value of the color, between 0x00 (decimal 0) and 0xFF (decimal 255)
	 * @return the color in ARGB format
	 */
	public static int color(int red, int green, int blue) {

		red = MathHelper.clamp(red, 0x00, 0xFF);
		green = MathHelper.clamp(green, 0x00, 0xFF);
		blue = MathHelper.clamp(blue, 0x00, 0xFF);

		final int alpha = 0xFF;

		// 0x alpha red green blue
		// 0xaarrggbb

		// int colorRGBA = 0;
		// colorRGBA |= red << 16;
		// colorRGBA |= green << 8;
		// colorRGBA |= blue << 0;
		// colorRGBA |= alpha << 24;

		return blue | red << 16 | green << 8 | alpha << 24;

	}

	/**
	 * @param red   the red value of the color, 0F and 1F
	 * @param green the green value of the color, 0F and 1F
	 * @param blue  the blue value of the color, 0F and 1F
	 * @return the color in ARGB format
	 */
	public static int colorf(final float red, final float green, final float blue) {
		final int redInt = Math.max(0, Math.min(255, Math.round(red * 255)));
		final int greenInt = Math.max(0, Math.min(255, Math.round(green * 255)));
		final int blueInt = Math.max(0, Math.min(255, Math.round(blue * 255)));
		return color(redInt, greenInt, blueInt);
	}

	/**
	 * A vertex definition for a simple 2-dimensional quad defined in counter-clockwise order with the top-left origin.
	 */
	public static final Vector4f[] SIMPLE_QUAD = {
			new Vector4f(1, 1, 0, 0),
			new Vector4f(1, 0, 0, 0),
			new Vector4f(0, 0, 0, 0),
			new Vector4f(0, 1, 0, 0)
	};

	public static int getLightmapSkyLightCoordsFromPackedLightmapCoords(int packedLightmapCoords) {
		return (packedLightmapCoords >> 16) & 0xFFFF; // get upper 4 bytes
	}

	public static int getLightmapBlockLightCoordsFromPackedLightmapCoords(int packedLightmapCoords) {
		return packedLightmapCoords & 0xFFFF; // get lower 4 bytes
	}

	// Below are some helper methods to upload data to the buffer for use by FastTESRs

	/**
	 * Renders a simple 2 dimensional quad at a given position to a given buffer with the given transforms, color, texture and lightmap values.
	 *
	 * @param baseOffset         the base offset. This will be untouched by the model matrix transformations.
	 * @param buffer             the buffer to upload the quads to. Vertex format of BLOCK is assumed.
	 * @param transform          the model matrix to use as the transform matrix.
	 * @param color              the color of the quad. The format is ARGB where each component is represented by a byte.
	 * @param texture            the TextureAtlasSprite object to gain the UV data from.
	 * @param lightmapSkyLight   the skylight lightmap coordinates for the quad.
	 * @param lightmapBlockLight the blocklight lightmap coordinates for the quad.
	 */
	public static void renderSimpleQuad(Vector3f baseOffset, BufferBuilder buffer, Matrix4f transform, int color, TextureAtlasSprite texture, int lightmapSkyLight, int lightmapBlockLight) {
		renderCustomQuad(SIMPLE_QUAD, baseOffset, buffer, transform, color, texture, lightmapSkyLight, lightmapBlockLight);
	}

	// add or subtract from the sprites UV location to remove transparent lines in between textures
	private static final float UV_CORRECT = 1F / 16F / 10000;

	/**
	 * Renders a simple 2 dimensional quad at a given position to a given buffer with the given transforms, color, texture and lightmap values.
	 *
	 * @param baseOffset         the base offset. This will be untouched by the model matrix transformations.
	 * @param buffer             the buffer to upload the quads to. Vertex format of BLOCK is assumed.
	 * @param transform          the model matrix to use as the transform matrix.
	 * @param color              the color of the quad. The format is ARGB where each component is represented by a byte.
	 * @param texture            the TextureAtlasSprite object to gain the UV data from.
	 * @param lightmapSkyLight   the skylight lightmap coordinates for the quad.
	 * @param lightmapBlockLight the blocklight lightmap coordinates for the quad.
	 */
	public static void renderCustomQuad(final Vector4f[] customQuad, Vector3f baseOffset, BufferBuilder buffer, Matrix4f transform, int color, TextureAtlasSprite texture, int lightmapSkyLight, int lightmapBlockLight) {
		// A quad consists of 4 vertices so the loop is executed 4 times.
		for (int i = 0; i < 4; ++i) {
			// Getting the vertex position from a set of predefined positions for a basic quad.
			Vector4f quadPos = customQuad[i];

			// Transforming the position vector by the transform matrix.
			quadPos = Matrix4f.transform(transform, quadPos, new Vector4f());

			// Getting the RGBA values from the color. To put it another way unpacking an int representation of a color to a 4-component float vector representation.
			float r = ((color & 0xFF0000) >> 16) / 255F;
			float g = ((color & 0xFF00) >> 8) / 255F;
			float b = (color & 0xFF) / 255F;
			float a = ((color & 0xFF000000) >> 24) / 255F;

			// Getting the texture UV coordinates from an index. The quad looks like this
			/*0 3
			  1 2*/
			float u = i < 2 ? texture.getMaxU() - UV_CORRECT : texture.getMinU() + UV_CORRECT;
			float v = i == 1 || i == 2 ? texture.getMaxV() - UV_CORRECT : texture.getMinV() + UV_CORRECT;

			// Uploading the quad data to the buffer.
			buffer.pos(quadPos.x + baseOffset.x, quadPos.y + baseOffset.y, quadPos.z + baseOffset.z).color(r, g, b, a).tex(u, v).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
		}
	}

	/**
	 * Renders a collection of BakedQuads into the BufferBuilder given. This method allows you to render any model in game in the FastTESR, be it a block model or an item model.
	 * Alternatively a custom list of quads may be constructed at runtime to render things like text.
	 * Drawbacks: doesn't transform normals as they are not guaranteed to be present in the buffer. Not relevant for a FastTESR but may cause issues with Optifine's shaders.
	 *
	 * @param quads      the iterable of BakedQuads. This may be any iterable object.
	 * @param baseOffset the base position offset for the rendering. This position will not be transformed by the model matrix.
	 * @param pipeline   the vertex consumer object. It is a parameter for optimization reasons. It may simply be constructed as new VertexBufferConsumer(buffer) and may be reused indefinately in the scope of the render pass.
	 * @param buffer     the buffer to upload vertices to.
	 * @param transform  the model matrix that is used to transform quad vertices.
	 * @param brightness the brightness of the model. The packed lightmap coordinate system is pretty complex and a lot of parameters are not necessary here so only the dominant one is implemented.
	 * @param color      the color of the quad. This is a color multiplier in the ARGB format.
	 */
	public static void renderQuads(Iterable<BakedQuad> quads, Vector3f baseOffset, VertexBufferConsumer pipeline, BufferBuilder buffer, Matrix4f transform, float brightness, int color) {
		// Get the raw int buffer of the buffer builder object.
		IntBuffer intBuf = getIntBuffer(buffer);

		// Iterate the iterable
		for (BakedQuad quad : quads) {
			// Push the quad to the consumer so it can be uploaded onto the buffer.
			LightUtil.putBakedQuad(pipeline, quad);

			// After the quad has been uploaded the buffer contains enough info to apply the model matrix transformation.
			// Getting the vertex size for the given format.
			int vertexSize = buffer.getVertexFormat().getIntegerSize();

			// Getting the offset for the current quad.
			int quadOffset = (buffer.getVertexCount() - 4) * vertexSize;

			// Each quad is made out of 4 vertices, so looping 4 times.
			for (int k = 0; k < 4; ++k) {
				// Getting the offset for the current vertex.
				int vertexIndex = quadOffset + k * vertexSize;

				// Grabbing the position vector from the buffer.
				float vertX = Float.intBitsToFloat(intBuf.get(vertexIndex));
				float vertY = Float.intBitsToFloat(intBuf.get(vertexIndex + 1));
				float vertZ = Float.intBitsToFloat(intBuf.get(vertexIndex + 2));
				Vector4f vert = new Vector4f(vertX, vertY, vertZ, 1);

				// Transforming it by the model matrix.
				vert = Matrix4f.transform(transform, vert, new Vector4f());

				// Uploading the difference back to the buffer. Have to use the helper function since the provided putX methods upload the data for a quad, not a vertex and this data is vertex-dependent.
				putPositionForVertex(buffer, intBuf, vertexIndex, new Vector3f(vert.x - vertX, vert.y - vertY, vert.z - vertZ));
			}

			// Uploading the origin position to the buffer. This is an addition operation.
			buffer.putPosition(baseOffset.x, baseOffset.y, baseOffset.z);

			// Constructing the most basic packed lightmap data with a mask of 0x00FF0000.
			int bVal = ((byte) (brightness * 255)) << 16;

			// Uploading the brightness to the buffer.
			buffer.putBrightness4(bVal, bVal, bVal, bVal);

			// Uploading the color multiplier to the buffer
			buffer.putColor4(color);
		}
	}

	/**
	 * A helper method that grabs all BakedQuads of a given model of a given IBlockState and joins them onto a single iterable.
	 *
	 * @param state the block state object to get the quads from.
	 * @return the iterable of BakedQuads.
	 */
	public static Iterable<BakedQuad> iterateQuadsOfBlock(IBlockState state) {
		return Arrays.stream(EnumFacing.values()).map(q -> Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state).getQuads(state, q, 0L)).flatMap(Collection::stream).distinct().collect(Collectors.toList());
	}

	/**
	 * A setter for the vertex-based positions for a given BufferBuilder object.
	 *
	 * @param buffer the buffer to set the positions in.
	 * @param intBuf the raw int buffer.
	 * @param offset the offset for the int buffer, in ints.
	 * @param pos    the position to add to the buffer.
	 */
	public static void putPositionForVertex(BufferBuilder buffer, IntBuffer intBuf, int offset, Vector3f pos) {
		// Getting the old position data in the buffer currently.
		float ox = Float.intBitsToFloat(intBuf.get(offset));
		float oy = Float.intBitsToFloat(intBuf.get(offset + 1));
		float oz = Float.intBitsToFloat(intBuf.get(offset + 2));

		// Converting the new data to ints.
		int x = Float.floatToIntBits(pos.x + ox);
		int y = Float.floatToIntBits(pos.y + oy);
		int z = Float.floatToIntBits(pos.z + oz);

		// Putting the data into the buffer
		intBuf.put(offset, x);
		intBuf.put(offset + 1, y);
		intBuf.put(offset + 2, z);
	}

	/**
	 * A field reference to the rawIntBuffer of the BufferBuilder class. Need reflection since the field is private.
	 */
	private static final Field bufferBuilder_rawIntBuffer = ReflectionHelper.findField(BufferBuilder.class, "rawIntBuffer", "field_178999_b");

	/**
	 * A getter for the rawIntBuffer field value of the BufferBuilder.
	 *
	 * @param buffer the buffer builder to get the buffer from
	 * @return the rawIntbuffer component
	 */
	public static IntBuffer getIntBuffer(BufferBuilder buffer) {
		try {
			return (IntBuffer) bufferBuilder_rawIntBuffer.get(buffer);
		} catch (IllegalAccessException e) {
			// Some other mod messed up and reset the access flag of the field.
			FMLCommonHandler.instance().raiseException(e, "An impossible error has occurred!", true);
		}

		return null;
	}

}
