package io.github.cadiboo.examplemod.client.render.tileentity;

import io.github.cadiboo.examplemod.client.ClientUtil;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.tileentity.TileEntityExampleTileEntity;
import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nonnull;

/**
 * @author V0IDW4LK3R
 * @author Cadiboo
 */
public class RenderExampleTileEntity extends FastTESR<TileEntityExampleTileEntity> {

	/**
	 * The render method that gets called for your FastTESR implementation. This is where you render things.
	 *
	 * @param tileEntity   your TileEntity instance.
	 * @param x            the X position of the TE in view space.
	 * @param y            the Y position of the TE in view space.
	 * @param z            the Z position of the TE in view space.
	 * @param partialTicks the amount of partial ticks escaped. Partial ticks happen when there are multiple frames per tick.
	 * @param destroyStage the destroy progress of the TE. You may use it to render the "breaking" animation.
	 * @param partial      currently seems to be a 1.0 constant.
	 * @param buffer       the BufferBuilder containing vertex data for vertices being rendered. It is safe to assume that the format is {@link DefaultVertexFormats#BLOCK}. It is also safe to assume that the GL primitive for drawing is QUADS.
	 */
	@Override
	public void renderTileEntityFast(@Nonnull final TileEntityExampleTileEntity tileEntity, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float partial, @Nonnull final BufferBuilder buffer) {
		final ResourceLocation registryName = ModBlocks.EXAMPLE_TILE_ENTITY.getRegistryName();
		final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(registryName.getNamespace(), "block/" + registryName.getPath()).toString());

		// rotations need to be in radians
		final float quarterRotation = (float) Math.PI / 2;
		final float rotation = (float) ModUtil.map(getWorld().getTotalWorldTime() + partialTicks % 360, 0, 360, -Math.PI, Math.PI);

		// the 4 forward rotating quads
		for (int i = 0; i < 4; i++) {
			ClientUtil.renderSimpleQuad(
					new Vector3f((float) x + 0.5F, (float) y, (float) z + 0.5F),
					buffer,
					new Matrix4f()
							.rotate(rotation + quarterRotation * i, new Vector3f(0, 1, 0)),
					ClientUtil.color(0xFF, 0xFF, 0xFF),
					sprite,
					240,
					0
			);
		}
		// the 4 backward rotating quads
		for (int i = 0; i < 4; i++) {
			ClientUtil.renderSimpleQuad(
					new Vector3f((float) x + 0.5F, (float) y, (float) z + 0.5F),
					buffer,
					new Matrix4f()
							.rotate(rotation + quarterRotation * i, new Vector3f(0, -1, 0)),
					ClientUtil.color(0xFF, 0xFF, 0xFF),
					sprite,
					240,
					0
			);
		}
		//TODO top & translations
		// the bottom horizontal quad
		final Matrix4f matrixBottom = Matrix4f.mul(
				Matrix4f
						.rotate(quarterRotation,
								new Vector3f(1, 0, 0),
								Matrix4f.setIdentity(new Matrix4f()),
								Matrix4f.setIdentity(new Matrix4f())
						)
				,
				Matrix4f
						.rotate(
								rotation,
								new Vector3f(0, 0, 1),
								Matrix4f.setIdentity(new Matrix4f()),
								Matrix4f.setIdentity(new Matrix4f())
						)
				,
				new Matrix4f()
		).scale(new Vector3f(0.5F, 0.5F, 0.5F)
		);
		ClientUtil.renderSimpleQuad(
				new Vector3f((float) x + 0.5F, (float) y, (float) z + 0.5F),
				buffer,
				matrixBottom,
				ClientUtil.color(0xFF, 0xFF, 0xFF),
				sprite,
				240,
				0
		);

	}

}
