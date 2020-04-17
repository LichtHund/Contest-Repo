package me.mattstudios.outerspace.commands

import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Matt
 */
@Command("moon")
class MoonCommand : CommandBase() {

    @Default
    fun default(player: Player) {
        player.teleport(Location(Bukkit.getWorld(Constants.WORLD_NAME), 50.0, 50.0, 50.0))
    }

    @SubCommand("delete")
    fun delete(player: CommandSender) {
        val world = Bukkit.getWorld("ar452b") ?: return
        Bukkit.unloadWorld(world, true)
        world.worldFolder.deleteRecursively()
    }

}