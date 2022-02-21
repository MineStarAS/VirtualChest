package kr.kro.minestar.virtualinventory.functions.chest

import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.virtualinventory.Main
import kr.kro.minestar.virtualinventory.functions.ChestClass.openedChest
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import java.io.File

class Chest(override val player: Player, number: Int) : GUI {
    override val pl = Main.pl

    val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}$number.yml")


    override val gui = Bukkit.createInventory(null, 9 * 6, "[${number + 1} 번 창고]")

    init {
        openedChest.add(this)
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        val data = YamlConfiguration.loadConfiguration(file)
        for (key in data.getKeys(false)) {
            val i = key.toIntOrNull() ?: continue
            if (i !in 0 until 9 * 6) continue
            if (!data.isItemStack(key)) continue
            gui.setItem(i, data.getItemStack(key))
        }
    }

    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        HandlerList.unregisterAll(this)
        openedChest.remove(this)
        save()
    }

    fun save() {
        val data = YamlConfiguration()
        for ((i, item) in gui.withIndex()) {
            data[i.toString()] = item
        }
        data.save(file)
    }
}