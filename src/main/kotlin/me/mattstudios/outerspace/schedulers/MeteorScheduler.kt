package me.mattstudios.outerspace.schedulers

import me.mattstudios.outerspace.OuterWorld
import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit

/**
 * @author Matt
 */
class MeteorScheduler(private val plugin: OuterWorld) : Runnable {

    override fun run() {
        val player = Bukkit.getOnlinePlayers().filter { it.world.name == Constants.WORLD_NAME }.random() ?: return

        MeteorShower(plugin, player.world, player.location).runTaskTimer(plugin, 20L, 20L)
    }

}