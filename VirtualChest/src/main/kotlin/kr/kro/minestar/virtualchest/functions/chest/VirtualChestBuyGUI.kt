package kr.kro.minestar.virtualchest.functions.chest

import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.item.addSuffix
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.number.addComma
import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.virtualchest.Main
import kr.kro.minestar.virtualchest.Main.Companion.prefix
import kr.kro.minestar.virtualchest.functions.ItemClass.head
import net.projecttl.economy.EconomyAPI
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import java.io.File

class VirtualChestBuyGUI(override val player: Player) : GUI {
    override val pl = Main.pl
    val file = File("${Main.pl.dataFolder}/${player.uniqueId}", "ChestMenu.yml")
    val data = YamlConfiguration.loadConfiguration(file)
    override val gui = Bukkit.createInventory(null, 9, "[가상창고 구매]")

    val unlockedItem = head(21771).display("§a[§f구매완료§a]")
    val lockedItem = head(9345).display("§c[§f구매§c] §f:")

    val priceList = listOf(
        0,
        1000000,
        3000000,
        5000000,
        10000000,
        30000000,
        50000000,
        100000000,
        300000000,
    )

    val money = EconomyAPI(player, pl)

    init {
        openGUI()
    }

    override fun displaying() {
        gui.clear()
        for (int in 0..8) {
            if (data.getBoolean("unlocked.$int")) gui.setItem(int, unlockedItem.clone())
            else {
                gui.setItem(int, lockedItem.clone().addSuffix("${priceList[int].addComma()} §e원"))
            }
        }
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return
        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) return
        if (e.click != ClickType.LEFT) return
        val item = e.currentItem ?: return
        val price = priceList[e.slot]
        if (item == unlockedItem) return
        if (money.money < price) return "$prefix §c돈이 모자릅니다.".toPlayer(player)
        money.subtractMoney(price)
        data["unlocked.${e.slot}"] = true
        data.save(file)
        "$prefix §e1 번 창고 §f구매 완료.".toPlayer(player)
        displaying()
    }
}