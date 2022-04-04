package kr.kro.minestar.virtualchest.api

import kr.kro.minestar.virtualchest.functions.ChestClass
import kr.kro.minestar.virtualchest.functions.MaterialChest
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object VirtualChestClass {
    private fun materialChest(player: Player, chestName: String): MaterialChest? {
        val map = ChestClass.playerChest[player] ?: return null
        return map[chestName]
    }

    fun addItem(player: Player, chestName: String, item: ItemStack): Boolean {
        val chest = materialChest(player, chestName) ?: return false
        return chest.addItem(item)
    }
}
