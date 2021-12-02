package kr.kro.minestar.virtualinventory.functions

import kr.kro.minestar.utility.inventory.howManyHasSameItem
import kr.kro.minestar.utility.inventory.howManyToAdd
import kr.kro.minestar.utility.item.*
import kr.kro.minestar.utility.number.addComma
import kr.kro.minestar.virtualinventory.Main.Companion.pl
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File

interface VirtualInventory : Listener {
    val title: String
    val iconItem: ItemStack
    val player: Player
    val file: File
    val data: YamlConfiguration
    val map: HashMap<Slot, Int>
    val blockList: List<Material>
    val gui: Inventory

    fun init() {
        if (!createFile()) load()
        Bukkit.getPluginManager().registerEvents(this, pl)
    }

    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        if (player != e.player) return
        save()
        HandlerList.unregisterAll(this)
    }

    @EventHandler
    fun breakBlock(e: BlockBreakEvent) {
        if (e.player != player) return
        if (!blockList.contains(e.block.type)) return
        e.isDropItems = false
        val items = e.block.getDrops(player.inventory.itemInMainHand)
        for (item in items) addItem(item)
    }

    @EventHandler
    fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        val item = e.currentItem ?: return
        if (e.clickedInventory == e.view.topInventory) when (e.click) {
            ClickType.LEFT -> takeOutItem(item.type.item(), 64)
            ClickType.SHIFT_LEFT -> takeOutItem(item.type.item(), player.inventory.howManyToAdd(item.type.item()))
        }
        if (e.clickedInventory == e.view.bottomInventory) when (e.click) {
            ClickType.LEFT -> insertItem(item)
            ClickType.SHIFT_LEFT -> insertAllItem(item)
        }
    }

    fun displaying() {
        gui.clear()
        val keys = map.keys
        for (s in keys) {
            val item = s.item.clone()
            item.addLore("§7[보유량] ${map[s]!!.addComma()} 개")
            item.addLore(" ")
            item.addLore("§8[좌클릭] 한 세트 꺼내기")
            item.addLore("§8[쉬프트 좌클릭] 최대 갯수 꺼내기")
            gui.setItem(s.get, item)
        }
    }

    fun openGUI() {
        displaying()
        player.openInventory(gui)
    }


    fun createFile(): Boolean {
        if (file.exists()) return false
        for (pair in map) data[pair.key.item.type.toString()] = pair.value
        data.save(file)
        return true
    }

    fun save() {
        for (s in map.keys) data[s.item.type.toString()] = map[s]
        data.save(file)
    }

    fun load() {
        for (key in data.getKeys(false)) {
            val k = getKey(Material.getMaterial(key ?: continue)?.item() ?: continue) ?: continue
            map[k] = data.getInt(key)
        }
    }

    fun addItem(item: ItemStack) {
        val slot = getKey(item) ?: return
        val amount = item.amount
        map[slot] = map[slot]!! + amount
    }

    fun takeOutItem(item: ItemStack, amount: Int) {
        val slot = getKey(item) ?: return
        val inv = player.inventory
        var a = amount
        val can = inv.howManyToAdd(slot.item)
        if (a > can) a = can
        if (map[slot]!! < a) a = map[slot]!!
        map[slot] = map[slot]!! - a
        inv.addItem(slot.item.clone().amount(a))
        displaying()
    }

    fun insertItem(item: ItemStack) {
        if (!item.isDefaultItem()) return
        val key = getKey(item) ?: return
        map[key] = map[key]!! + item.amount
        item.amount = 0
        displaying()
    }

    fun insertAllItem(item: ItemStack) {
        if (!item.isDefaultItem()) return
        val key = getKey(item) ?: return
        val inv = player.inventory
        val has = inv.howManyHasSameItem(item)
        map[key] = map[key]!! + has
        inv.removeItem(item.clone().amount(has))
        displaying()
    }

    fun getKey(item: ItemStack): Slot? {
        for (s in map.keys) if (s.item.isSameItem(item)) return s
        return null
    }

}