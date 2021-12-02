package kr.kro.minestar.virtualinventory

import kr.kro.minestar.virtualinventory.events.AlwaysEvent
import kr.kro.minestar.virtualinventory.functions.Util
import kr.kro.minestar.virtualinventory.functions.VirtualInventory
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9인벤토리§f]"
    }

    val map: HashMap<Player, MutableList<VirtualInventory>> = HashMap()

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("inventory")?.setExecutor(CMD)
        Bukkit.getPluginManager().registerEvents(AlwaysEvent(), this)
        for (player in Bukkit.getOnlinePlayers()) registerInventories(player)
    }

    override fun onDisable() {
        for (l in map.values) for (c in l) c.save()
    }


    fun registerInventories(player: Player) {
        val path = "kr.kro.minestar.virtualinventory.functions.inventory"
        val utilClass = Util()
        val classes = utilClass.getClasses(path)
        if (!map.contains(player)) map[player] = mutableListOf()
        for (c in classes) {
            val s = "$path.${c.simpleName}"
            val a = Class.forName(s)
            val b = a.constructors[0].newInstance(player) as VirtualInventory
            map[player]!!.add(b)
        }
    }
}