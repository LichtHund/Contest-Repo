package me.mattstudios.outerspace

import me.mattstudios.mattcore.MattPlugin
import me.mattstudios.mattcore.utils.MessageUtils.info
import me.mattstudios.outerspace.commands.AbductionCommand
import me.mattstudios.outerspace.config.Settings
import me.mattstudios.outerspace.generator.PlanetChunkGenerator
import me.mattstudios.outerspace.listeners.PlayerListeners
import me.mattstudios.outerspace.schedulers.MeteorScheduler
import me.mattstudios.outerspace.schedulers.RainScheduler
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

        config.load(Settings.javaClass)

        // Registers handlers
        registerCommands(AbductionCommand(this))
        registerListeners(PlayerListeners())

        // Starts events, meteor shower and toxic rain
        // Runs every 5 minutes
        Bukkit.getScheduler().runTaskTimer(this, MeteorScheduler(this), 6000L, 6000L)
        // Runs every 5 ticks
        Bukkit.getScheduler().runTaskTimer(this, RainScheduler(), 5L, 5L)
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