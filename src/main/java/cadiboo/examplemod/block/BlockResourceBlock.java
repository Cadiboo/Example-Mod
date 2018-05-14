package cadiboo.examplemod.block;

import cadiboo.examplemod.handler.EnumHandler.ResourceBlocks;
import net.minecraft.block.material.Material;

public class BlockResourceBlock extends BlockBase {

	private final ResourceBlocks resource;

	public BlockResourceBlock(String nameIn, Material materialIn, ResourceBlocks resourceIn) {
		super(nameIn, materialIn);
		this.setBeaconBase();
		this.resource = resourceIn;
	}

}
