package me.mattstudios.outerspace.listeners

import me.mattstudios.outerspace.utils.Constants
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author Matt
 */
class PlayerListeners : Listener {

    /**
     * Handles texture pack set and remove
     */
    @EventHandler
    fun PlayerChangedWorldEvent.onPlayerChanceWorld() {
        // Checks if they are coming from the ar452b planet
        if (from.name == Constants.WORLD_NAME) {
            // Sets the resource pack to an empty zip to remove the resource pack "because Mojang"
            player.setResourcePack(Constants.DEFAULT_RESOURCE_PACK_URL)
            return
        }

        if (player.world.name != Constants.WORLD_NAME) return

        // Sets the OuterWorld texture pack when the player joins the world
        player.setResourcePack(Constants.OW_RESOURCE_PACK_URL)
    }

    /**
     * Checks if player is logging back into the world
     */
    @EventHandler
    fun PlayerJoinEvent.onPlayerJoin() {
        if (player.world.name != Constants.WORLD_NAME) return

        // Sets the OuterWorld texture pack when the player joins the world
        player.setResourcePack(Constants.OW_RESOURCE_PACK_URL)
    }

}