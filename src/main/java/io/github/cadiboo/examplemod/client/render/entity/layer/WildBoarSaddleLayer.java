package io.github.cadiboo.examplemod.client.render.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.entity.WildBoarEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

/**
 * Copy of {@link SaddleLayer} with tweaks to make it work for WildBoarEntity.
 *
 * @author Cadiboo
 */
public class WildBoarSaddleLayer extends LayerRenderer<WildBoarEntity, PigModel<WildBoarEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MODID, "textures/entity/wild_boar/wild_boar_saddle.png");
	private final PigModel<WildBoarEntity> pigModel = new PigModel<>(0.5F);

	public WildBoarSaddleLayer(IEntityRenderer<WildBoarEntity, PigModel<WildBoarEntity>> p_i50927_1_) {
		super(p_i50927_1_);
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, WildBoarEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		if (entity.getSaddled()) {
			this.getEntityModel().setModelAttributes(this.pigModel);
			this.pigModel.setLivingAnimations(entity, p_225628_5_, p_225628_6_, p_225628_7_);
			this.pigModel.render(entity, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
			IVertexBuilder buffer = renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
			this.pigModel.render(matrixStack, buffer, light, OverlayTexture.DEFAULT_LIGHT, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

}
