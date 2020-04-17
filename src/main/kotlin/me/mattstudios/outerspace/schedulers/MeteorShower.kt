package me.mattstudios.outerspace.schedulers

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
        if (getTimeSinceStart() >= 30) cancel()

        val randomX = (-15..15).random().toDouble()
        val randomZ = (-15..15).random().toDouble()

        val spawnLocation = mainLocation.clone()
        spawnLocation.y = world.maxHeight - 50.0
        spawnLocation.add(randomX, 0.0, randomZ)
        spawnLocation.pitch = 89.85F
        spawnLocation.yaw = -251.85F

        val toLocation = spawnLocation.clone()
        toLocation.y = 62.0

        val meteor = world.spawnEntity(spawnLocation, EntityType.FIREBALL) as Fireball

        meteor.velocity = toLocation.subtract(spawnLocation).toVector().normalize().multiply(0.5)
        meteor.setIsIncendiary(false)
        meteor.yield = 5.0F

        MeteorParticles(meteor).runTaskTimer(plugin, 0L, 1L)

        //fireBall.setIsIncendiary(true)

        println("spawning")
    }

    /**
     * Gets the difference in seconds since start
     */
    private fun getTimeSinceStart(): Long {
        return TimeUnit.SECONDS.convert(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS)
    }

}