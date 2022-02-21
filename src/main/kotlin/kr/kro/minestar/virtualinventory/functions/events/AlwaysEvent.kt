package kr.kro.minestar.virtualinventory.functions.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object AlwaysEvent : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
    }
}