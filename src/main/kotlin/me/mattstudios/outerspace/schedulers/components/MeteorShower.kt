package me.mattstudios.outerspace.schedulers.components

import me.mattstudios.outerspace.OuterWorld
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.Fireball
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

/**
 * @author Matt
 */
class MeteorShower(private val plugin: OuterWorld, private val world: World, private val mainLocation: Location) : BukkitRunnable() {

    private val startTime = System.currentTimeMillis()

    override fun run() {
        // Cancels the rain after a minute of shower
        if (getTimeSinceStart() >= 60) cancel()

        // Random 15 blocks around the start location
        val randomX = (-15..15).random().toDouble()
        val randomZ = (-15..15).random().toDouble()

        // Sets spawn location and makes the fireballs aim straight down
        val spawnLocation = mainLocation.clone()
        spawnLocation.y = world.maxHeight - 50.0
        spawnLocation.add(randomX, 0.0, randomZ)
        spawnLocation.pitch = 89.85F
        spawnLocation.yaw = -251.85F

        // Gets the ground location to aim the ball and add slight curve
        val toLocation = spawnLocation.clone().add((-2..2).random().toDouble(), 0.0, (-2..2).random().toDouble())
        toLocation.y = 62.0

        // Spawns the fireball
        val meteor = world.spawnEntity(spawnLocation, EntityType.FIREBALL) as Fireball

        // Sets the fireball to go towards the location
        meteor.velocity = toLocation.subtract(spawnLocation).toVector().normalize().multiply(0.5)
        meteor.setIsIncendiary(false)
        // Sets the explosion strength
        meteor.yield = 5.0F

        // Starts runnable to spawn it's falling trail
        MeteorParticles(meteor).runTaskTimer(plugin, 0L, 1L)
    }

    /**
     * Gets the difference in seconds since start
     */
    private fun getTimeSinceStart(): Long {
        return TimeUnit.SECONDS.convert(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS)
    }

}