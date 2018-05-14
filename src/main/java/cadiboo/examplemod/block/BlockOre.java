package cadiboo.examplemod.block;

import java.util.Random;

import cadiboo.examplemod.handler.EnumHandler.Ores;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOre extends BlockBase {

	private final Ores ore;

	public BlockOre(String nameIn, Material materialIn, Ores oreIn) {
		super(nameIn, materialIn);
		this.ore = oreIn;
		this.setHarvestLevel("Stone", 3);
		this.setHardness(3.0F);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		return super.quantityDroppedWithBonus(fortune, random);
	}

}
