package kr.kro.minestar.virtualchest.functions.material.chest

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.virtualchest.Main
import kr.kro.minestar.virtualchest.functions.MaterialChest
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.Inventory
import java.io.File

class MineralChest(override val player: Player) : MaterialChest {
    override val pl = Main.pl
    override val title = "[광물 인벤토리]"
    override val iconItem = Material.DIAMOND.item().display("§b[§f광물 인벤토리§b]")
    override val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    override val data = YamlConfiguration.loadConfiguration(file)
    override val itemMap: HashMap<Slot, Int> = hashMapOf(
        Pair(Slot(0, 0, Material.COBBLESTONE.item()), 0),
        Pair(Slot(0, 1, Material.COBBLED_DEEPSLATE.item()), 0),
        Pair(Slot(0, 2, Material.COAL.item()), 0),
        Pair(Slot(0, 3, Material.LAPIS_LAZULI.item()), 0),
        Pair(Slot(0, 4, Material.RAW_COPPER.item()), 0),
        Pair(Slot(0, 5, Material.RAW_IRON.item()), 0),
        Pair(Slot(0, 6, Material.RAW_GOLD.item()), 0),
        Pair(Slot(0, 7, Material.EMERALD.item()), 0),
        Pair(Slot(0, 8, Material.DIAMOND.item()), 0),

        Pair(Slot(1, 0, Material.STONE.item()), 0),
        Pair(Slot(1, 1, Material.DEEPSLATE.item()), 0),
        Pair(Slot(1, 4, Material.COPPER_INGOT.item()), 0),
        Pair(Slot(1, 5, Material.IRON_INGOT.item()), 0),
        Pair(Slot(1, 6, Material.GOLD_INGOT.item()), 0),
    )
    override val blockList = listOf(
        Material.STONE,
        Material.COBBLESTONE,
        Material.DEEPSLATE,
        Material.COBBLED_DEEPSLATE,
        Material.COAL_ORE,
        Material.LAPIS_ORE,
        Material.COPPER_ORE,
        Material.IRON_ORE,
        Material.GOLD_ORE,
        Material.EMERALD_ORE,
        Material.DIAMOND_ORE,
        Material.DEEPSLATE_COAL_ORE,
        Material.DEEPSLATE_LAPIS_ORE,
        Material.DEEPSLATE_COPPER_ORE,
        Material.DEEPSLATE_IRON_ORE,
        Material.DEEPSLATE_GOLD_ORE,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.DEEPSLATE_DIAMOND_ORE,
    )
    override val gui: Inventory = Bukkit.createInventory(null, 9 * 2, title)

    init {
        init()
    }

    @EventHandler
    fun breakBlock(e: BlockBreakEvent) {
        if (e.player != player) return
        if (e.player.gameMode == GameMode.CREATIVE) return
        if (e.isCancelled) return
        if (!blockList.contains(e.block.type)) return
        e.isDropItems = false
        val items = e.block.getDrops(player.inventory.itemInMainHand)
        for (item in items) addItem(item)
    }
}