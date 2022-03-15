package kr.kro.minestar.virtualchest.api

import kr.kro.minestar.virtualchest.functions.chest.Chest
import kr.kro.minestar.virtualchest.functions.chest.ChestMenu
import kr.kro.minestar.virtualchest.functions.chest.VirtualChestBuyGUI
import org.bukkit.entity.Player

object VirtualChestOpenGUI {
    fun chest(player: Player, int: Int) {
        Chest(player, int)
    }

    fun chestMenu(player: Player) {
        ChestMenu(player)
    }

    fun virtualChestBuyGUI(player: Player) {
        VirtualChestBuyGUI(player)
    }
}