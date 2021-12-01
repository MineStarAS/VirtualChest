package kr.kro.minestar.virtualinventory.functions.inventory

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.item
import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.virtualinventory.Main.Companion.pl
import kr.kro.minestar.virtualinventory.functions.interfaces.VirtualInventory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.io.File

class WoolInventory(override val player: Player) : VirtualInventory {
    override val title = "[양털 인벤토리]"
    override val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    override val data = YamlConfiguration.loadConfiguration(file)
    override val map: HashMap<Slot, Int> = hashMapOf(
        Pair(Slot(0, 0, Material.BLACK_WOOL.item()), 0),
        Pair(Slot(0, 1, Material.WHITE_WOOL.item()), 0),
    )
    override val blockList = listOf(
        Material.BLACK_WOOL,
        Material.WHITE_WOOL,
    )
    override val gui: Inventory = Bukkit.createInventory(null, 9 * 2, title)

    init {
        init()
    }
}