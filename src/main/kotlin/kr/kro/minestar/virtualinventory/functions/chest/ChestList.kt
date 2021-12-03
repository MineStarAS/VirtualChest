package kr.kro.minestar.virtualinventory.functions.chest

import kr.kro.minestar.utility.item.item
import kr.kro.minestar.utility.item.setDisplay
import kr.kro.minestar.virtualinventory.Main.Companion.pl
import kr.kro.minestar.virtualinventory.functions.VirtualInventory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.io.File

class ChestList(val player: Player) : Listener {
    val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    val data = YamlConfiguration.loadConfiguration(file)
    val array = booleanArrayOf(true, false, false, false, false, false, false, false, false)
    val invMap = hashMapOf<ItemStack, VirtualInventory>()
    val gui = Bukkit.createInventory(null, 9 * 2, "[가상창고 목록]")

    init {
        if (!createFile()) load()
        setMap()
        openGUI()
    }

    fun createFile(): Boolean {
        if (file.exists()) return false
        for ((i, b) in array.withIndex()) data[i.toString()] = b
        data.save(file)
        return true
    }

    fun save() {
        for ((i, b) in array.withIndex()) data[i.toString()] = b
        data.save(file)
    }

    fun load() {
        for (i in 0 until 9) array[i] = data.getBoolean(i.toString())
    }

    fun setMap() {
        val list = pl.map[player] ?: return
        if (list.isEmpty()) return
        for (c in list) invMap[c.iconItem] = c
    }

    @EventHandler
    fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        val item = e.currentItem ?: return
        val slot = e.slot
        if (e.clickedInventory == e.view.topInventory) when (e.click) {
            ClickType.LEFT -> {
                if (slot in 0 until 9) if (item.type == Material.CHEST) return openChest(slot)
                openVirtualInventory(item)
            }
        }
    }

    @EventHandler
    fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        HandlerList.unregisterAll(this)
    }

    fun displaying() {
        gui.clear()
        for ((i, b) in array.withIndex()) gui.setItem(i, chestItem(b, i))

        val list = pl.map[player] ?: return
        if (list.isEmpty()) return
        for ((i, item) in invMap.keys.withIndex()) gui.setItem(9 + i, item)
    }

    fun openGUI() {
        displaying()
        Bukkit.getPluginManager().registerEvents(this, pl)
        player.openInventory(gui)
    }

    fun unlockChest(int: Int) {
        if (array.size <= int) return
        array[int] = true
        save()
    }

    fun openChest(int: Int) {
        if (array.size <= int) return
        if (!array[int]) return
        Chest(player, int)
    }

    fun openVirtualInventory(item: ItemStack) {
        invMap[item]?.openGUI()
    }

    fun chestItem(boolean: Boolean, int: Int): ItemStack {
        val item = if (boolean) Material.CHEST.item()
        else Material.BARRIER.item()
        item.setDisplay("§e[§f${int + 1} 번 창고§e]")
        return item
    }
}