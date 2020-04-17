package me.mattstudios.outerspace

import me.mattstudios.mattcore.MattPlugin
import me.mattstudios.mattcore.utils.MessageUtils.info
import me.mattstudios.outerspace.commands.MoonCommand
import me.mattstudios.outerspace.generator.PlanetChunkGenerator
import me.mattstudios.outerspace.listeners.PlayerListeners
import me.mattstudios.outerspace.schedulers.MeteorScheduler
import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.WorldCreator


/**
 * @author Matt
 */
class OuterWorld : MattPlugin() {

    /**
     * Plugin enable
     */
    override fun enable() {
        createWorld()

        registerCommands(MoonCommand())
        registerListeners(PlayerListeners())

        Bukkit.getScheduler().runTaskTimer(this, MeteorScheduler(this), 20L, 600L)
    }

    /**
     * Creates the planet world
     */
    private fun createWorld() {
        info("&cCreating the world")
        // Sets the custom world generator
        val worldCreator = WorldCreator(Constants.WORLD_NAME)
        worldCreator.generator(PlanetChunkGenerator())

        // Creates the world and sets it to hard
        val world = worldCreator.createWorld() ?: return
        world.difficulty = Difficulty.HARD
    }

}