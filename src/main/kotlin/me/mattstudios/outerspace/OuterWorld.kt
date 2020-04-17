package me.mattstudios.outerspace

import me.mattstudios.mattcore.MattPlugin
import me.mattstudios.outerspace.commands.MoonCommand
import me.mattstudios.outerspace.generator.PlanetChunkGenerator
import me.mattstudios.outerspace.listeners.PlayerListeners
import me.mattstudios.outerspace.utils.Constants
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
    }

    /**
     * Creates the planet world
     */
    private fun createWorld() {
        // Sets the custom world generator
        val worldCreator = WorldCreator(Constants.WORLD_NAME)
        worldCreator.generator(PlanetChunkGenerator())

        // Creates the world and sets it to hard
        val world = worldCreator.createWorld() ?: return
        world.difficulty = Difficulty.HARD
    }

}