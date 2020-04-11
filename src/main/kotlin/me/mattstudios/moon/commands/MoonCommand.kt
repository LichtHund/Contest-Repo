package me.mattstudios.moon.commands

import com.sun.org.apache.bcel.internal.generic.FADD
import me.mattstudios.mattcore.utils.Task.later
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.moon.generator.MoonChunkGenerator
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.generator.ChunkGenerator
import java.io.File

/**
 * @author Matt
 */
@Command("moon")
class MoonCommand : CommandBase() {

    @Default
    fun default(player: Player) {
        player.teleport(Location(Bukkit.getWorld("moon"), 50.0, 50.0, 50.0))
    }

    @SubCommand("delete")
    fun delete(player: CommandSender) {
        val world = Bukkit.getWorld("moon") ?: return
        Bukkit.unloadWorld(world, true)
        world.worldFolder.deleteRecursively()
    }

}