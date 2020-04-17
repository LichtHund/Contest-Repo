package me.mattstudios.outerspace.commands

import me.mattstudios.mattcore.utils.MessageUtils.color
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.outerspace.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Matt
 */
@Command("abduct")
class AbductionCommand : CommandBase() {

    @Default
    @Permission("outerworld.abduct")
    fun default(sender: CommandSender, player: Player?) {
        // Checks if valid player was introduced
        if (player == null) {
            sender.sendMessage(color("&cPlease select a valid Player!"))
            return
        }

        // Sends them to the world's spawn
        val world = Bukkit.getWorld(Constants.WORLD_NAME) ?: return
        player.teleport(world.spawnLocation)
    }

}