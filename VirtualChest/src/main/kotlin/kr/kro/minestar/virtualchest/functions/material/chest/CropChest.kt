package kr.kro.minestar.virtualchest.functions.material.chest

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import kr.kro.minestar.virtualchest.Main
import kr.kro.minestar.virtualchest.functions.MaterialChest
import org.bukkit.*
import org.bukkit.block.data.Ageable
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerHarvestBlockEvent
import org.bukkit.inventory.Inventory
import java.io.File

class CropChest(override val player: Player) : MaterialChest {
    override val pl = Main.pl
    override val title = "[작물 인벤토리]"
    override val iconItem = Material.WHEAT.item().display("§a[§f작물 인벤토리§a]")
    override val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    override val data = YamlConfiguration.loadConfiguration(file)
    override val itemMap: HashMap<Slot, Int> = hashMapOf(
        Pair(Slot(0, 0, Material.WHEAT_SEEDS.item()), 0),
        Pair(Slot(0, 3, Material.BEETROOT_SEEDS.item()), 0),
        Pair(Slot(0, 4, Material.MELON_SEEDS.item()), 0),
        Pair(Slot(0, 5, Material.PUMPKIN_SEEDS.item()), 0),

        Pair(Slot(1, 0, Material.WHEAT.item()), 0),
        Pair(Slot(1, 1, Material.CARROT.item()), 0),
        Pair(Slot(1, 2, Material.POTATO.item()), 0),
        Pair(Slot(1, 3, Material.BEETROOT.item()), 0),
        Pair(Slot(1, 4, Material.MELON.item()), 0),
        Pair(Slot(1, 5, Material.PUMPKIN.item()), 0),
        Pair(Slot(1, 6, Material.COCOA_BEANS.item()), 0),
        Pair(Slot(1, 7, Material.SUGAR_CANE.item()), 0),
        Pair(Slot(1, 8, Material.SWEET_BERRIES.item()), 0),

        Pair(Slot(2, 4, Material.MELON_SLICE.item()), 0),
        )
    override val blockList = listOf(
        Material.WHEAT,
        Material.WHEAT_SEEDS,
        Material.CARROTS,
        Material.POTATOES,
        Material.BEETROOTS,
        Material.MELON,
        Material.MELON_STEM,
        Material.PUMPKIN,
        Material.PUMPKIN_STEM,
        Material.COCOA,
        Material.SUGAR_CANE,
        Material.SWEET_BERRY_BUSH,
    )
    override val gui: Inventory = Bukkit.createInventory(null, 9 * 3, title)

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

    @EventHandler
    fun harvestSweetBerries(e: PlayerHarvestBlockEvent) {
        if (player != e.player) return
        val block = e.harvestedBlock
        if (block.type != Material.SWEET_BERRY_BUSH) return
        e.isCancelled = true
        val blockData = block.blockData as Ageable
        blockData.age = 1
        block.blockData = blockData
        player.playSound(player.location, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1F, 1F)
        for (i in e.itemsHarvested) addItem(i)
    }
}