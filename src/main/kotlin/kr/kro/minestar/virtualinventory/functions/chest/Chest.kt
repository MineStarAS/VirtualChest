package kr.kro.minestar.virtualinventory.functions.chest

import kr.kro.minestar.virtualinventory.Main
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.io.File

class Chest(val player: Player, val number: Int) : Listener {
    val file = File("${Main.pl.dataFolder}/${player.uniqueId}", "${this.javaClass.simpleName}$number.yml")
    val data = YamlConfiguration.loadConfiguration(file)
    val gui = Bukkit.createInventory(null, 9 * 6, "[${number + 1} 번 창고]")
}