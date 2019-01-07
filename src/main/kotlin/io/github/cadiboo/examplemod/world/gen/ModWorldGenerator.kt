package io.github.cadiboo.examplemod.world.gen

import io.github.cadiboo.examplemod.init.ModBlocks
import io.github.cadiboo.examplemod.util.ModUtil
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.Random

/**
 * Basic world generator that generates ores
 *
 * @author Cadiboo
 */
class ModWorldGenerator : IWorldGenerator {

    /* These values are very similar to Iron */
    const val OVERWORLD_MIN_Y = 8
    const val OVERWORLD_MAX_Y = 64
    const val OVERWORLD_SIZE = 6
    const val OVERWORLD_CHANCE = 4

    @Override
    fun generate(@Nonnull random: Random, chunkX: Int, chunkZ: Int, @Nonnull world: World, @Nonnull chunkGenerator: IChunkGenerator, @Nonnull chunkProvider: IChunkProvider) {
        when (world.provider.getDimensionType()) {
            NETHER -> {
            }
            OVERWORLD -> this.generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider)
            THE_END -> {
            }
            else -> {
            }
        }
    }

    private fun generateOverworld(@Nonnull random: Random, chunkX: Int, chunkZ: Int, @Nonnull world: World, @Nonnull chunkGenerator: IChunkGenerator, @Nonnull chunkProvider: IChunkProvider) {
        this.generateOre(ModBlocks.EXAMPLE_ORE.getDefaultState(), world, random, chunkX shl 4, chunkZ shl 4, OVERWORLD_MIN_Y, OVERWORLD_MAX_Y, OVERWORLD_SIZE, OVERWORLD_CHANCE)
    }

    private fun generateOre(@Nonnull ore: IBlockState, @Nonnull world: World, @Nonnull random: Random, x: Int, z: Int, minY: Int, maxY: Int, size: Int, chances: Int) {
        for (chance in 0 until chances) {
            val pos = BlockPos(x + random.nextInt(16), minY + ModUtil.randomBetween(minY, maxY), z + random.nextInt(16))
            val generator = WorldGenMinable(ore, size)
            generator.generate(world, random, pos)
        }
    }

}
