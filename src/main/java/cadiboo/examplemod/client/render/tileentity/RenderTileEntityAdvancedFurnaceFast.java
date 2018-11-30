package cadiboo.examplemod.client.render.tileentity;

import cadiboo.examplemod.tileentity.TileEntityAdvancedFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class RenderTileEntityAdvancedFurnaceFast extends FastTESR<TileEntityAdvancedFurnace>
{
	/**
	 * A field reference to the rawIntBuffer of the BufferBuilder class. Need reflection since the field is private.
	 */
	private static final Field bufferBuilder_rawIntBuffer = ReflectionHelper.findField(BufferBuilder.class, "rawIntBuffer", "field_178999_b");

	/**
	 * A vertex definition for a simple 2-dimensional quad defined in counter-clockwise order with the top-left origin.
	 */
	private static final Vector4f[] simpleQuad = new Vector4f[]{ new Vector4f(1, 1, 0, 0), new Vector4f(1, 0, 0, 0), new Vector4f(0, 0, 0, 0), new Vector4f(0, 1, 0, 0) };

	/**
	 * The render method that gets called for your FastTESR implementation. This is where you render things.
	 * @param advancedFurnace your TileEntity instance.
	 * @param x the X position of the TE in view space.
	 * @param y the Y position of the TE in view space.
	 * @param z the Z position of the TE in view space.
	 * @param partialTicks the amount of partial ticks escaped. Partial ticks happen when there are multiple frames per tick.
	 * @param destroyStage the destroy progress of the TE. You may use it to render the "breaking" animation.
	 * @param partial currently seems to be a 1.0 constant.
	 * @param buffer the BufferBuilder containing vertex data for vertices being rendered. It is safe to assume that the format is {@link net.minecraft.client.renderer.vertex.DefaultVertexFormats DefaultVertexFormats}.BLOCK. It is also safe to assume that the GL primitive for drawing is QUADS.
	 */
	@Override
	public void renderTileEntityFast(final TileEntityAdvancedFurnace advancedFurnace, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float partial, final BufferBuilder buffer) {

	}

	// Below are some helper methods to upload data to the buffer

	/**
	 * Renders a simple 2 dimensional quad at a given position to a given buffer with the given transforms, color, texture and lightmap values.
	 * @param baseOffset the base offset. This will be untouched by the model matrix transformations.
	 * @param buffer the buffer to upload the quads to. Vertex format of BLOCK is assumed.
	 * @param transform the model matrix to use as the transform matrix.
	 * @param color the color of the quad. The format is ARGB where each component is represented by a byte.
	 * @param texture the TextureAtlasSprite object to gain the UV data from.
	 * @param lightmap the lightmap coordinates for the quad.
	 */
	public void renderSimpleQuad(Vector3f baseOffset, BufferBuilder buffer, Matrix4f transform, int color, TextureAtlasSprite texture, int... lightmap)
	{
		// A quad consists of 4 vertices so the loop is executed 4 times.
		for (int i = 0; i < 4; ++i)
		{
			// Getting the vertex position from a set of predefined positions for a basic quad.
			Vector4f quadPos = simpleQuad[i];

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
			float u = i < 2 ? texture.getMaxU() : texture.getMinU();
			float v = i == 1 || i == 2 ? texture.getMaxV() : texture.getMinV();

			// Uploading the quad data to the buffer.
			buffer.pos(quadPos.x + baseOffset.x, quadPos.y + baseOffset.y, quadPos.z + baseOffset.z).color(r, g, b, a).tex(u, v).lightmap(lightmap[0], lightmap[1]).endVertex();
		}
	}

	/**
	 * Renders a collection of BakedQuads into the BufferBuilder given. This method allows you to render any model in game in the FastTESR, be it a block model or an item model.
	 * Alternatively a custom list of quads may be constructed at runtime to render things like text.
	 * Drawbacks: doesn't transform normals as they are not guaranteed to be present in the buffer. Not relevant for a FastTESR but may cause issues with Optifine's shaders.
	 * @param quads the iterable of BakedQuads. This may be any iterable object.
	 * @param baseOffset the base position offset for the rendering. This position will not be transformed by the model matrix.
	 * @param pipeline the vertex consumer object. It is a parameter for optimization reasons. It may simply be constructed as new VertexBufferConsumer(buffer) and may be reused indefinately in the scope of the render pass.
	 * @param buffer the buffer to upload vertices to.
	 * @param transform the model matrix that is used to transform quad vertices.
	 * @param brightness the brightness of the model. The packed lightmap coordinate system is pretty complex and a lot of parameters are not necessary here so only the dominant one is implemented.
	 * @param color the color of the quad. This is a color multiplier in the ARGB format.
	 */
	public void renderQuads(Iterable<BakedQuad> quads, Vector3f baseOffset, VertexBufferConsumer pipeline, BufferBuilder buffer, Matrix4f transform, float brightness, int color)
	{
		// Get the raw int buffer of the buffer builder object.
		IntBuffer intBuf = getIntBuffer(buffer);

		// Iterate the iterable
		for (BakedQuad quad : quads)
		{
			// Push the quad to the consumer so it can be uploaded onto the buffer.
			LightUtil.putBakedQuad(pipeline, quad);

			// After the quad has been uploaded the buffer contains enough info to apply the model matrix transformation.
			// Getting the vertex size for the given format.
			int vertexSize = buffer.getVertexFormat().getIntegerSize();

			// Getting the offset for the current quad.
			int quadOffset = (buffer.getVertexCount() - 4) * vertexSize;

			// Each quad is made out of 4 vertices, so looping 4 times.
			for (int k = 0; k < 4; ++k)
			{
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
			int bVal = ((byte)(brightness * 255)) << 16;

			// Uploading the brightness to the buffer.
			buffer.putBrightness4(bVal, bVal, bVal, bVal);

			// Uploading the color multiplier to the buffer
			buffer.putColor4(color);
		}
	}

	/**
	 * A helper method that grabs all BakedQuads of a given model of a given IBlockState and joins them onto a single iterable.
	 * @param state the block state object to get the quads from.
	 * @return the iterable of BakedQuads.
	 */
	protected static Iterable<BakedQuad> iterateQuadsOfBlock(IBlockState state)
	{
		return Arrays.stream(EnumFacing.values()).map(q -> Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state).getQuads(state, q, 0L)).flatMap(Collection::stream).distinct().collect(Collectors.toList());
	}

	/**
	 * A setter for the vertex-based positions for a given BufferBuilder object.
	 * @param buffer the buffer to set the positions in.
	 * @param intBuf the raw int buffer.
	 * @param offset the offset for the int buffer, in ints.
	 * @param pos the position to add to the buffer.
	 */
	protected static void putPositionForVertex(BufferBuilder buffer, IntBuffer intBuf, int offset, Vector3f pos)
	{
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
	 * A getter for the rawIntBuffer field value of the BufferBuilder.
	 * @param buffer the buffer builder to get the buffer from
	 * @return the rawIntbuffer component
	 */
	protected static IntBuffer getIntBuffer(BufferBuilder buffer)
	{
		try
		{
			return (IntBuffer) bufferBuilder_rawIntBuffer.get(buffer);
		}
		catch (IllegalAccessException e)
		{
			// Some other mod messed up and reset the access flag of the field.
			FMLCommonHandler.instance().raiseException(e, "An impossible error has occurred!", true);
		}

		return null;
	}
}
