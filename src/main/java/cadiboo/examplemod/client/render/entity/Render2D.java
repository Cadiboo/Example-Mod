package cadiboo.examplemod.client.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Render2D<T extends Entity> extends Render<T> {

	protected final ResourceLocation	texture;
	private float						scale;

	public Render2D(RenderManager renderManagerIn, ResourceLocation texture, float scale) {
		super(renderManagerIn);
		this.texture = texture;
		this.scale = scale;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		bindTexture(texture);
		GlStateManager.scale(this.scale, this.scale, this.scale);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(getTeamColor(entity));
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		bufferbuilder.pos(-1, -1, -1).tex(0, 1).endVertex();
		bufferbuilder.pos(-1, 1, -1).tex(0, 0).endVertex();
		bufferbuilder.pos(1, 1, -1).tex(1, 0).endVertex();
		bufferbuilder.pos(1, -1, -1).tex(1, 1).endVertex();

		tessellator.draw();

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		GlStateManager.enableLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.LOCATION_MISSING_TEXTURE;
	}
}
