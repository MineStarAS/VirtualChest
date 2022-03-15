package kr.kro.minestar.virtualchest.functions.chest

import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.item.isSameItem
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.virtualchest.Main
import kr.kro.minestar.virtualchest.functions.ChestClass.playerChest
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.io.File

class ChestMenu(override val player: Player) : GUI {
    override val pl = Main.pl
    val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    val data = YamlConfiguration.loadConfiguration(file)
    override val gui = Bukkit.createInventory(null, 9 * 2, "[가상창고 목록]")

    init {
        if (!file.exists()) {
            data["unlocked.0"] = true
            for (int in 1..8) data["unlocked.$int"] = false
            data.save(file)
        }
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (int in 0..8) gui.setItem(int, chestItem(data.getBoolean("unlocked.$int"), int))
        for ((int, chest) in playerChest[player]!!.values.withIndex()) gui.setItem(int + 9, chest.iconItem)
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        val item = e.currentItem ?: return
        val slot = e.slot
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        if (slot in 0 until 9) {
            if (item.type == Material.CHEST) return openChest(slot)
            else if (item.type == Material.BARRIER) VirtualChestBuyGUI(player)
            return
        }
        for (chest in playerChest[player]!!.values) if (chest.iconItem.isSameItem(item)) chest.openGUI()
    }


    fun openChest(int: Int) {
        if (int !in 0..8) return
        if (!data.getBoolean("unlocked.$int")) return
        Chest(player, int)
    }

    fun chestItem(boolean: Boolean, int: Int): ItemStack {
        val item = if (boolean) Material.CHEST.item()
        else Material.BARRIER.item()
        item.display("§e[§f${int + 1} 번 창고§e]")
        return item
    }
}
