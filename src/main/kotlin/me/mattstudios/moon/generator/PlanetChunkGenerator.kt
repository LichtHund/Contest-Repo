package me.mattstudios.moon.generator

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.util.noise.PerlinOctaveGenerator
import java.util.ArrayList
import java.util.Random
import kotlin.math.pow


/**
 * @author Matt
 */
class MoonChunkGenerator : ChunkGenerator() {

    //    private var currentHeight = 50
    private val seaLevel = 62
    private val medianLevel = 70

    private var lacunarity = 2.0
    private var persistence = 0.5

    /**
     * Generates the moon chunks
     */
    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biome: BiomeGrid): ChunkData {
        val chunk = createChunkData(world)

        // Creates the perlin octave generators used
        val topLayerPerlin = PerlinOctaveGenerator(world.seed, 4)
        val elevationPerlin = PerlinOctaveGenerator(world.seed, 4)
        val detailPerlin = PerlinOctaveGenerator(world.seed, 4)
        val roughPerlin = PerlinOctaveGenerator(world.seed, 1)

        // Sets all the generator's scale
        topLayerPerlin.setScale(1 / 150.0)
        elevationPerlin.setScale(1 / 200.0)
        detailPerlin.setScale(1 / 30.0)
        roughPerlin.setScale(1 / 100.0)

        // Cycles through the chunk
        for (x in 0..15) {
            for (z in 0..15) {
                val realX = x + chunkX * 16
                val realZ = z + chunkZ * 16

                // Gets the noise values for each perlin
                val elevationNoise = elevationPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(0), persistence.pow(0), false)
                val detailNoise = detailPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(1), persistence.pow(1), false)
                val roughNoise = roughPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(2), persistence.pow(2), false)

                // Gets the max height of the noise
                var maxHeight = ((elevationNoise + detailNoise * roughNoise) * seaLevel + medianLevel).toInt()

                // Gets a random value for the top layer
                val topHeight = (detailPerlin.noise(realX.toDouble(), realZ.toDouble(), 2.5, 0.35, false) * 15 + 150).toInt()

                // Makes sure the max height will never be higher than the top layer
                if (maxHeight >= topHeight) maxHeight = topHeight

                // Cycles through the top height and the world height to fill in the top layer
                for (y in topHeight..world.maxHeight) {
                    when (y) {
                        // Top stone layer
                        in (248..254).random()..world.maxHeight -> setBlock(chunk, x, y, z, Material.STONE, biome, Biome.STONE_SHORE)

                        // Lower packed ice layer
                        in y..(topHeight + 45) -> setBlock(chunk, x, y, z, Material.PACKED_ICE, biome, Biome.FROZEN_OCEAN)

                        // Middle blue ice layer
                        else -> setBlock(chunk, x, y, z, Material.BLUE_ICE, biome, Biome.DEEP_FROZEN_OCEAN)
                    }
                }

                // Gets a random
                val bedrockMax = (1..5).random()

                // Sets the bedrock layer
                for (y in 0..bedrockMax) setBlock(chunk, x, y, z, Material.BEDROCK)

                // Fills the rest of the height with stone
                for (y in bedrockMax..maxHeight) setBlock(chunk, x, y, z, Material.STONE)

                // Checks if the height is less than sea level
                if (maxHeight < seaLevel) {
                    for (y in maxHeight..62) setBlock(chunk, x, y, z, Material.WATER, biome, Biome.WARM_OCEAN)

                    // underwater sand blocks
                    setBlock(chunk, x, maxHeight - 1, z, Material.SAND, biome, Biome.WARM_OCEAN)
                    setBlock(chunk, x, maxHeight - 2, z, Material.SAND, biome, Biome.WARM_OCEAN)
                    if (Math.random() > 0.5) setBlock(chunk, x, maxHeight - 3, z, Material.SAND, biome, Biome.WARM_OCEAN)

                    continue
                }

                // surface grass blocks
                setBlock(chunk, x, maxHeight, z, Material.GRASS_BLOCK)
                //setBlock(chunk, x, maxHeight - 1, z, Material.DIRT)
                //if (Math.random() > 0.5) setBlock(chunk, x, maxHeight - 2, z, Material.DIRT)


                //biome.setBiome(x, z, Biome.PLAINS)
            }
        }

        /*val generator = PerlinOctaveGenerator(Random(world.seed), 3)
        generator.setScale(0.0125)

        for (x in 0..15) for (z in 0..15) {
            currentHeight = (generator.noise(chunkX * 16.0 + x, chunkZ * 16.0 + z, 1.0, 2.0) * 15.0 + 50.0).toInt()
            for (height in currentHeight downTo 1) chunk.setBlock(x, height, z, Material.GRASS_BLOCK)
            chunk.setBlock(x, 0, z, Material.BEDROCK)
        }*/

        return chunk
    }

    private fun setBlock(chunk: ChunkData, x: Int, y: Int, z: Int, material: Material, biomeData: BiomeGrid? = null, biome: Biome? = null) {
        if (biome != null && biomeData != null) biomeData.setBiome(x, y, z, biome)
        chunk.setBlock(x, y, z, material)
    }

    override fun getDefaultPopulators(world: World): MutableList<BlockPopulator> {
        return ArrayList()
    }

    override fun getFixedSpawnLocation(world: World, random: Random): Location {
        val x = random.nextInt(200) - 100
        val z = random.nextInt(200) - 100
        val y = world.getHighestBlockYAt(x, z)
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }


}