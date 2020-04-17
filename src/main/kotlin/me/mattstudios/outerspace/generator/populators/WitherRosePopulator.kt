package me.mattstudios.outerspace.generator.populators

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.generator.BlockPopulator
import java.util.Random

/**
 * @author Matt
 */
class WitherRosePopulator : BlockPopulator() {

    private val witherRoseChance = 90
    private val maxFlowersPerChunk = 5
    private val minFlowersPerChunk = 2

    /**
     * Populates the world with craters
     */
    override fun populate(world: World, random: Random, chunk: Chunk) {
        // Chance to generate
        if ((1..100).random() > witherRoseChance) return

        // Tries generating a random amount of flowers per chunk
        repeat((minFlowersPerChunk..maxFlowersPerChunk).random()) {
            // Gets the real coordinates of the blocks
            val realX = (0..15).random() + chunk.x * 16
            val realZ = (0..15).random() + chunk.z * 16

            val highestY = world.getHighestBlockYAt(realX, realZ)

            // Makes sure it's not going to generate roses above water
            if (highestY >= 63) {
                val flowerBlock = world.getBlockAt(realX, highestY + 1, realZ)
                val underBlockType = flowerBlock.getRelative(BlockFace.DOWN).type

                // Only creates flower is the block is not solid and is on top of grass
                if (underBlockType == Material.GRASS_BLOCK && !flowerBlock.type.isSolid) {
                    flowerBlock.type = Material.WITHER_ROSE
                }
            }
        }
    }

}