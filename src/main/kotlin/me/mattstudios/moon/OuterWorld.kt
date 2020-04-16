package me.mattstudios.moon

import me.mattstudios.mattcore.MattPlugin
import me.mattstudios.moon.commands.MoonCommand
import me.mattstudios.moon.generator.PlanetChunkGenerator
import org.bukkit.GameRule
import org.bukkit.WorldCreator
import org.bukkit.event.Listener


/**
 * @author Matt
 */
class OuterWorld : MattPlugin(), Listener {

    override fun enable() {
        val worldCreator = WorldCreator("moon")
        worldCreator.generator(PlanetChunkGenerator(worldCreator.seed()))
        worldCreator.createWorld()

        val world = worldCreator.createWorld() ?: return
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.time = 6000

        registerCommands(MoonCommand())
        registerListeners(this)

    }

}