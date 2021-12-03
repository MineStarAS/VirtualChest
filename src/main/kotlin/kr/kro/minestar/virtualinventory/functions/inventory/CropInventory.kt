package kr.kro.minestar.virtualinventory.functions.inventory

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.item
import kr.kro.minestar.utility.item.setDisplay
import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.virtualinventory.Main.Companion.pl
import kr.kro.minestar.virtualinventory.functions.VirtualInventory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.data.Ageable
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.player.PlayerHarvestBlockEvent
import org.bukkit.inventory.Inventory
import java.io.File

class CropInventory(override val player: Player) : VirtualInventory {
    override val title = "[작물 인벤토리]"
    override val iconItem = Material.WHEAT.item().setDisplay("§a[§f작물 인벤토리§a]")
    override val file = File("${pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}.yml")
    override val data = YamlConfiguration.loadConfiguration(file)
    override val map: HashMap<Slot, Int> = hashMapOf(
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
    fun test(e: PlayerHarvestBlockEvent) {
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