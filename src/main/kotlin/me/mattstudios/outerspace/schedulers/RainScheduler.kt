package me.mattstudios.outerspace.schedulers

import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.Sound

/**
 * @author Matt
 */
class RainScheduler : Runnable {

    private val planet = Bukkit.getWorld(Constants.WORLD_NAME)

    override fun run() {
        // Checks whether or not the planet exists and is raining
        if (planet == null) return
        if (!planet.hasStorm()) return

        // Gets all players in the planet and checks if they are in the rain
        planet.players.forEach {
            if (it.location.block.lightFromSky.toInt() != 15) return@forEach

            // Damages and plays sound
            it.damage(.5)
            planet.playSound(it.location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, .5f, .5f)
        }
    }
}