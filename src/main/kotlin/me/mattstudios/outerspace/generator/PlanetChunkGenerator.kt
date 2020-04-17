package me.mattstudios.outerspace.generator

import me.mattstudios.outerspace.generator.populators.SurfaceCratersPopulator
import me.mattstudios.outerspace.generator.populators.WitherRosePopulator
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.util.noise.PerlinOctaveGenerator
import java.util.Random
import kotlin.math.pow


/**
 * @author Matt
 */
class PlanetChunkGenerator() : ChunkGenerator() {

    // Controls basic settings
    private val seaLevel = 62
    private val medianLevel = 70

    // Controls the frequency and amplitude variables
    private var lacunarity = 2.0
    private var persistence = 0.5

    /**
     * Generates the planet chunks
     *
     * Usage of deprecated setBiome and getBiome is due to the fact that 3D biomes
     * Are not fully implemented on 1.15 making it harder to use the 3D variation
     */
    @Suppress("DEPRECATION")
    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biomeGrid: BiomeGrid): ChunkData {
        // Creates the perlin octave generators used
        val elevationPerlin = PerlinOctaveGenerator(world.seed, 4)
        val detailPerlin = PerlinOctaveGenerator(world.seed, 4)
        val roughPerlin = PerlinOctaveGenerator(world.seed, 1)

        // Sets all the generator's scale
        elevationPerlin.setScale(1 / 800.0)
        detailPerlin.setScale(1 / 50.0)
        roughPerlin.setScale(1 / 200.0)

        val chunk = createChunkData(world)

        // Cycles through the chunk coordinates
        for (x in 0..15) for (z in 0..15) {
            val biome = biomeGrid.getBiome(x, z)

            // Gets the value of the real coordinates from the chunk
            val realX = x + chunkX * 16
            val realZ = z + chunkZ * 16

            // Gets the noise values for each perlin
            val elevationNoise = elevationPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(0), persistence.pow(0), false)
            val detailNoise = detailPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(1), persistence.pow(1), false)
            val roughNoise = roughPerlin.noise(realX.toDouble(), realZ.toDouble(), lacunarity.pow(2), persistence.pow(2), false)

            // Gets the max height of the noise
            var maxHeight = ((elevationNoise + detailNoise * roughNoise) * seaLevel + medianLevel).toInt()

            // Makes sure the max height will never be higher than the top layer
            if (maxHeight >= world.maxHeight) maxHeight = world.maxHeight

            // Gets a random
            val bedrockMax = (1..5).random()

            // Sets the bedrock layer
            for (y in 0..bedrockMax) setBlock(chunk, x, y, z, Material.BEDROCK)

            // Fills the rest of the height with stone
            for (y in bedrockMax..maxHeight) setBlock(chunk, x, y, z, Material.STONE)

            // Checks if the height is less than sea level
            if (maxHeight < seaLevel) {
                // Makes sure that the ocean floor doesn't get lower than 5 to prevent bedrock from being deleted
                if (maxHeight < 5) maxHeight = 5

                // Makes all ocean warm ocean due to the planet's temperature
                biomeGrid.setBiome(x, z, Biome.WARM_OCEAN)

                // Fills the ocean
                for (y in maxHeight..seaLevel) setBlock(chunk, x, y, z, Material.WATER)

                // Random chance to populate the ocean floor with gravel
                if ((1..100).random() <= 20) {
                    populateOceanFloor(chunk, x, maxHeight, z, Material.GRAVEL)
                    continue
                }

                // Populates the rest of the ocean floor with sand
                populateOceanFloor(chunk, x, maxHeight, z, Material.SAND)

                continue
            }

            // Biome changing conditions
            when (biome) {
                Biome.FOREST -> if (maxHeight >= 100) biomeGrid.setBiome(x, z, Biome.WOODED_HILLS)
                Biome.WOODED_HILLS -> if (maxHeight < 100) biomeGrid.setBiome(x, z, Biome.FOREST)

                Biome.DARK_FOREST -> if (maxHeight >= 100) biomeGrid.setBiome(x, z, Biome.DARK_FOREST_HILLS)
                Biome.DARK_FOREST_HILLS -> if (maxHeight < 100) biomeGrid.setBiome(x, z, Biome.DARK_FOREST)

                Biome.PLAINS -> if (maxHeight >= 100) biomeGrid.setBiome(x, z, Biome.WOODED_HILLS)

                else -> {
                    if (maxHeight > 100) biomeGrid.setBiome(x, z, Biome.BAMBOO_JUNGLE_HILLS)
                    else biomeGrid.setBiome(x, z, Biome.BAMBOO_JUNGLE)
                }
            }

            // surface grass blocks
            setBlock(chunk, x, maxHeight, z, Material.GRASS_BLOCK)
            setBlock(chunk, x, maxHeight - 1, z, Material.DIRT)

            if ((0..1).random() == 0) setBlock(chunk, x, maxHeight - 2, z, Material.DIRT)

        }

        return chunk
    }

    /**
     * Lets it generate vanilla caves
     */
    override fun shouldGenerateCaves(): Boolean {
        return true
    }

    /**
     * Lets vanilla populate the world with ores, trees, etc
     */
    override fun shouldGenerateDecorations(): Boolean {
        return true
    }

    /**
     * Lets the default mobs spawn
     */
    override fun shouldGenerateMobs(): Boolean {
        return true
    }

    /**
     * Sets the populators for the world
     */
    override fun getDefaultPopulators(world: World): List<BlockPopulator> {
        return listOf(SurfaceCratersPopulator(), WitherRosePopulator())
    }

    /**
     * Sets the spawn location
     */
    override fun getFixedSpawnLocation(world: World, random: Random): Location {
        val x = (-100..200).random()
        val z = (-100..200).random()
        val y = world.getHighestBlockYAt(x, z)

        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }

    /**
     * Sets the block and biome
     */
    private fun setBlock(chunk: ChunkData, x: Int, y: Int, z: Int, material: Material) {
        chunk.setBlock(x, y, z, material)
    }

    /**
     * Populates the ocean floor with the selected material
     */
    private fun populateOceanFloor(chunk: ChunkData, x: Int, y: Int, z: Int, material: Material) {
        setBlock(chunk, x, y - 1, z, material)
        setBlock(chunk, x, y - 2, z, material)

        if (Math.random() > 0.5) setBlock(chunk, x, y - 3, z, material)
    }

}