package me.mattstudios.outerspace.schedulers

import me.mattstudios.outerspace.OuterWorld
import me.mattstudios.outerspace.schedulers.components.MeteorShower
import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.Sound

/**
 * @author Matt
 */
class MeteorScheduler(private val plugin: OuterWorld) : Runnable {

    override fun run() {
        // Checks if should or not shower
        if ((1..100).random() >= Constants.METEOR_CHANCE) return

        // Gets a random player in the planet or returns if there is none
        val planetPlayers = Bukkit.getOnlinePlayers().filter { it.world.name == Constants.WORLD_NAME }
        if (planetPlayers.isEmpty()) return

        val player = planetPlayers.random()

        // Starts the meteor shower around the player
        player.world.playSound(player.location, Sound.EVENT_RAID_HORN, 1f, .5f)
        MeteorShower(plugin, player.world, player.location).runTaskTimer(plugin, 20L, 20L)
    }

}