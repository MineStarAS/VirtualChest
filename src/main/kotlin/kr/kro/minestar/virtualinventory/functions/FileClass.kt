package kr.kro.minestar.virtualinventory.functions

import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.virtualinventory.Main.Companion.pl
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

class FileClass(val player: Player) {

    val directory = File("${pl.dataFolder}/${player.uniqueId}")

    val mineralInvFile = File(directory, "Mineral.yml")
    val cropInvFile = File(directory, "Crop.yml")
    val woodInvFile = File(directory, "wood.yml")

    val mineralList = listOf(
        Slot(0, 0, ItemStack(Material.COBBLESTONE)),
        Slot(0, 1, ItemStack(Material.COBBLED_DEEPSLATE)),
        Slot(0, 2, ItemStack(Material.COAL)),
        Slot(0, 3, ItemStack(Material.LAPIS_LAZULI)),
        Slot(0, 4, ItemStack(Material.RAW_COPPER)),
        Slot(0, 5, ItemStack(Material.RAW_IRON)),
        Slot(0, 6, ItemStack(Material.RAW_GOLD)),
        Slot(0, 7, ItemStack(Material.EMERALD)),
        Slot(0, 8, ItemStack(Material.DIAMOND)),
    )

    fun createFiles() {
        if (!directory.exists()) directory.mkdir()

    }

    fun createFile() {

    }

    enum class Type {
        MINERAL, CROP, WOOD
    }
}