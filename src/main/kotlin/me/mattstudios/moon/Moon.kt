package me.mattstudios.moon

import me.mattstudios.mattcore.MattPlugin
import me.mattstudios.moon.commands.MoonCommand
import me.mattstudios.moon.generator.MoonChunkGenerator
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.GameRule
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockEvent
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList


/**
 * @author Matt
 */
class Moon : MattPlugin(), Listener {

    override fun enable() {
        val worldCreator = WorldCreator("moon")
        worldCreator.generator(MoonChunkGenerator())
        worldCreator.createWorld()
        val world = worldCreator.createWorld() ?: return
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)

        registerCommands(MoonCommand())
        registerListeners(this)
    }

    @EventHandler
    fun BlockEvent.test() {
        println(block.type.name)
    }

}