package kr.kro.minestar.virtualinventory.functions

import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.setDisplay
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestClass {
    fun test(player: Player) {
        val inv = player.inventory
        val item = ItemStack(Material.DIAMOND)
        item.setDisplay("TEST")
        item.addLore("ASD")
        if (!inv.contains(item)) inv.addItem(item)
    }
}