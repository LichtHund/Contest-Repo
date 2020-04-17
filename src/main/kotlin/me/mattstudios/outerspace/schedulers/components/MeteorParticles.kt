package me.mattstudios.outerspace.schedulers.components

import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author Matt
 */
class MeteorParticles(private val meteor: Entity) : BukkitRunnable() {

    override fun run() {
        if (meteor.isDead) cancel()

        // Meteor trail
        meteor.world.spawnParticle(Particle.SMOKE_LARGE, meteor.location, 15, .25, .25, .25, .02)
        meteor.world.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, meteor.location, 15, .25, .25, .25, .02)
    }

}