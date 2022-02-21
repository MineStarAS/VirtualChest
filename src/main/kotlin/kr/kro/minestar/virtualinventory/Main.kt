package kr.kro.minestar.virtualinventory

import kr.kro.minestar.virtualinventory.commands.ChestCMD
import kr.kro.minestar.virtualinventory.functions.events.AlwaysEvent
import kr.kro.minestar.virtualinventory.functions.ChestClass
import kr.kro.minestar.virtualinventory.functions.ChestClass.openedChest
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9가상창고§f]"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("chest")?.setExecutor(ChestCMD)
        Bukkit.getPluginManager().registerEvents(AlwaysEvent, this)
        for (player in Bukkit.getOnlinePlayers()) for (c in ChestClass.materialChestList) c.constructors.first().newInstance(player)
    }

    override fun onDisable() {
        for (chest in openedChest) chest.save()
    }
}