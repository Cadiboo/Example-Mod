package io.github.cadiboo.examplemod.world.gen;

import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Basic world generator that generates ores
 *
 * @author Cadiboo
 */
public class ModWorldGenerator implements IWorldGenerator {

	/* these values are very similar to Iron */
	public static final int OVERWORLD_MIN_Y = 8;
	public static final int OVERWORLD_MAX_Y = 64;
	public static final int OVERWORLD_SIZE = 6;
	public static final int OVERWORLD_CHANCE = 4;

	@Override
	public void generate(@Nonnull final Random random, final int chunkX, final int chunkZ, @Nonnull final World world, @Nonnull final IChunkGenerator chunkGenerator, @Nonnull final IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionType()) {
			case NETHER:
				break;
			case OVERWORLD:
				this.generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			case THE_END:
				break;
			default:
				break;

		}
	}

	private void generateOverworld(@Nonnull final Random random, final int chunkX, final int chunkZ, @Nonnull final World world, @Nonnull final IChunkGenerator chunkGenerator, @Nonnull final IChunkProvider chunkProvider) {
		this.generateOre(ModBlocks.EXAMPLE_ORE.getDefaultState(), world, random, chunkX << 4, chunkZ << 4, OVERWORLD_MIN_Y, OVERWORLD_MAX_Y, OVERWORLD_SIZE, OVERWORLD_CHANCE);
	}

	private void generateOre(@Nonnull final IBlockState ore, @Nonnull final World world, @Nonnull final Random random, final int x, final int z, final int minY, final int maxY, final int size, final int chances) {
		for (int chance = 0; chance < chances; chance++) {
			final BlockPos pos = new BlockPos(x + random.nextInt(16), minY + ModUtil.randomBetween(minY, maxY), z + random.nextInt(16));
			final WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}

}
