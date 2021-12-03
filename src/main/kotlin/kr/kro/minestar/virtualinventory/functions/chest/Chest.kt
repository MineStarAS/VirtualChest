package kr.kro.minestar.virtualinventory.functions.chest

import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.virtualinventory.Main
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import java.io.File

class Chest(val player: Player, val number: Int) : Listener {
    val file = File("${Main.pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}$number.yml")
    val data = YamlConfiguration.loadConfiguration(file)
    val gui = Bukkit.createInventory(null, 9 * 6, "[${number + 1} 번 창고]")

    init {
        if (!createFile()) load()
        openGUI()
    }

    fun createFile(): Boolean {
        if (file.exists()) return false
        data.save(file)
        return true
    }


    fun openGUI() {
        Bukkit.getPluginManager().registerEvents(this, Main.pl)
        player.openInventory(gui)
    }

    @EventHandler
    fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        save()
        HandlerList.unregisterAll(this)
    }

    fun save() {
        for ((i, item) in gui.withIndex()) {
            data[i.toString()] = item
        }
        data.save(file)
    }

    fun load() {
        gui.clear()
        for (key in data.getKeys(false)) {
            val i = key.toIntOrNull() ?: continue
            if (i !in 0 until 9 * 6) continue
            if (!data.isItemStack(key)) continue
            gui.setItem(i, data.getItemStack(key))
        }
    }

}