package kr.kro.minestar.virtualchest

import kr.kro.minestar.virtualchest.commands.ChestCMD
import kr.kro.minestar.virtualchest.functions.events.AlwaysEvent
import kr.kro.minestar.virtualchest.functions.ChestClass
import kr.kro.minestar.virtualchest.functions.ChestClass.openedChest
import kr.kro.minestar.virtualchest.functions.ChestClass.playerChest
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
        for (t in playerChest.values) for (c in t.values) c.save()
    }
}