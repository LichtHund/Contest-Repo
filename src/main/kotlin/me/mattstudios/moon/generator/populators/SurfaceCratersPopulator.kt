package me.mattstudios.moon.generator.populators

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import java.util.Random


/**
 * @author Matt
 */
class SurfaceCratersPopulator : BlockPopulator() {

    private val craterChance = 1

    private val smallCraterSize = 3
    private val mediumCraterSize = 8
    private val bigCraterSize = 16

    private val bigCrateChance = 10

    /**
     * Populates the world with craters
     */
    override fun populate(world: World, random: Random, chunk: Chunk) {
        // Chance to generate
        if ((1..100).random() > craterChance) return

        // Gets the crater center coordinates
        val centerX = (chunk.x shl 4) + (0..16).random()
        val centerZ = (chunk.z shl 4) + (0..16).random()
        val centerY = world.getHighestBlockYAt(centerX, centerZ)

        // Prevents crater from generating in the water
        if (centerY <=62) return

        // Makes center a location
        val center = Location(world, centerX.toDouble(), centerY.toDouble(), centerZ.toDouble())

        // Decides if the crater will be big medium or small
        val radius = if ((1..100).random() <= bigCrateChance) {
            (smallCraterSize..bigCraterSize).random()
        } else {
            (smallCraterSize..mediumCraterSize).random()
        }

        // Creates the crater sphere
        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    val position = center.clone().add(x.toDouble(), y.toDouble(), z.toDouble())
                    if (center.distance(position) > radius + 0.5) continue

                    val block = position.block

                    if (block.type == Material.AIR) continue

                    block.type = Material.AIR
                }
            }
        }
    }

}