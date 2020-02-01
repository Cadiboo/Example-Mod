package io.github.cadiboo.examplemod.client.gui;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.client.render.MiniModel;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

/**
 * A Screen for refreshing our MiniMode.
 * It contains two buttons "Refresh Mini Model" and "Done"
 *
 * @author Cadiboo
 */
public class MiniModelScreen extends Screen {

	private final MiniModelTileEntity tileEntity;

	public MiniModelScreen(final MiniModelTileEntity tileEntity) {
		super(ModBlocks.MINI_MODEL.get().getNameTextComponent());
		this.tileEntity = tileEntity;
	}

	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void init() {
		final int halfW = this.width / 2;
		final int halfH = this.height / 2;
		// "Refresh Mini Model" button rebuilds the tile's MiniModel
		this.addButton(new ExtendedButton(halfW - 150, halfH, 150, 20, I18n.format("gui." + ExampleMod.MODID + ".refresh_mini_model"),
				$ -> {
					final MiniModel miniModel = this.tileEntity.miniModel;
					if (miniModel != null)
						miniModel.compile();
				}
		));
		// "Done" button exits the GUI
		this.addButton(new ExtendedButton(halfW, halfH, 150, 20, I18n.format("gui.done"),
				$ -> this.minecraft.displayGuiScreen(null)
		));
		super.init();
	}

	@Override
	public boolean isPauseScreen() {
		return false; // Don't pause the game when this screen is open
	}

}
