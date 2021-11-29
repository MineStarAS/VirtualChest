package kr.kro.minestar.virtualinventory

import kr.kro.minestar.virtualinventory.events.AlwaysEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9PLUGIN]§f"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("cmd")?.setExecutor(CMD())
        Bukkit.getPluginManager().registerEvents(AlwaysEvent(), this)
    }

    override fun onDisable() {
    }
}