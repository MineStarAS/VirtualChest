package kr.kro.minestar.virtualchest.functions.events

import kr.kro.minestar.virtualchest.functions.ChestClass
import kr.kro.minestar.virtualchest.functions.ChestClass.playerChest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object AlwaysEvent : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
        for (c in ChestClass.materialChestList) c.constructors.first().newInstance(e.player)
    }

    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        for (c in playerChest[e.player]!!.values) c.save()
        playerChest.remove(e.player)
    }
}