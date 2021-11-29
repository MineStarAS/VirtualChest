package kr.kro.minestar.virtualinventory.functions

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.number.addComma
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File

interface VirtualInventory {
    val player: Player

    val file: File
    val data: YamlConfiguration

    val map: HashMap<Slot, Int>

    val gui: Inventory

    fun displaying() {
        gui.clear()
        val slots = map.keys
        for (slot in slots) {
            val item = slot.item.clone()
            item.addLore("§7[보유량]")
            item.addLore("§7${map[slot]!!.addComma()} 개")
            gui.setItem(slot.get, item)
        }
    }

    fun addItem(item: ItemStack) {
        if ()
        val amount = item.amount

    }

    fun contains(item: ItemStack): Boolean {
        val slots = map.keys
        for (slot in slots){

        }
    }

}