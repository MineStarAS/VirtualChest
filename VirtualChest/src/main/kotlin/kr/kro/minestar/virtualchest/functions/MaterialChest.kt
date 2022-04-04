package kr.kro.minestar.virtualchest.functions

import kr.kro.minestar.utility.event.disable
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.inventory.howManyHasSameItem
import kr.kro.minestar.utility.inventory.howManyToAdd
import kr.kro.minestar.utility.item.*
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.utility.number.addComma
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import java.io.File

interface MaterialChest : GUI {
    val title: String
    val iconItem: ItemStack
    val file: File
    val data: YamlConfiguration
    val itemMap: HashMap<Slot, Int>
    val blockList: List<Material>

    fun init() {
        if (!ChestClass.playerChest.contains(player)) ChestClass.playerChest[player] = hashMapOf()
        for (key in data.getKeys(false)) {
            val material = Material.valueOf(key)
            for (slot in itemMap.keys) {
                if (slot.item.type != material) continue
                itemMap[slot] = data.getInt(key)
                break
            }
        }
        val map = ChestClass.playerChest[player]!!
        map[javaClass.simpleName] = this
        enable(pl)
    }

    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        if (player != e.player) return
        save()
        disable()
    }

    override fun openGUI() {
        displaying()
        player.openInventory(gui)
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
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

    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
    }

    override fun displaying() {
        gui.clear()
        for (slot in itemMap.keys) {
            val item = slot.item.clone()
            item.addLore("§7[보유량] ${itemMap[slot]!!.addComma()} 개")
            item.addLore(" ")
            item.addLore("§8[좌클릭] 한 세트 꺼내기")
            item.addLore("§8[쉬프트 좌클릭] 최대 갯수 꺼내기")
            gui.setItem(slot.get, item)
        }
    }

    fun save() {
        for (s in itemMap) data[s.key.item.type.toString()] = s.value
        data.save(file)
    }

    fun addItem(item: ItemStack): Boolean {
        val slot = getKey(item) ?: return false
        val amount = item.amount
        itemMap[slot] = itemMap[slot]!! + amount
        return true
    }

    fun takeOutItem(item: ItemStack, amount: Int) {
        val slot = getKey(item) ?: return
        val inv = player.inventory
        var a = amount
        val can = inv.howManyToAdd(slot.item)
        if (a > can) a = can
        if (itemMap[slot]!! < a) a = itemMap[slot]!!
        itemMap[slot] = itemMap[slot]!! - a
        inv.addItem(slot.item.clone().amount(a))
        displaying()
    }

    fun insertItem(item: ItemStack) {
        if (!item.isDefaultItem()) return
        val key = getKey(item) ?: return
        itemMap[key] = itemMap[key]!! + item.amount
        item.amount = 0
        displaying()
    }

    fun insertAllItem(item: ItemStack) {
        if (!item.isDefaultItem()) return
        val key = getKey(item) ?: return
        val inv = player.inventory
        val has = inv.howManyHasSameItem(item)
        itemMap[key] = itemMap[key]!! + has
        inv.removeItem(item.clone().amount(has))
        displaying()
    }

    fun getKey(item: ItemStack): Slot? {
        for (s in itemMap.keys) if (s.item.isSameItem(item)) return s
        return null
    }

}