package me.mattstudios.outerspace.schedulers

import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author Matt
 */
class MeteorParticles(private val meteor: Entity) : BukkitRunnable() {

    override fun run() {
        if (meteor.isDead) {
            println("canceling")
            cancel()
        }

        meteor.world.spawnParticle(Particle.SMOKE_LARGE, meteor.location, 10, .2, .2, .2, 0.0)
        meteor.world.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, meteor.location, 10, .2, .2, .2, 0.0)
    }

}