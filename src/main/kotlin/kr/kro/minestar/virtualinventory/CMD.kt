package kr.kro.minestar.virtualinventory

import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.virtualinventory.functions.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private val args0 = listOf("open")
    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (p !is Player) return false
        if (!p.isOp) return false
        if (args.isEmpty()) {
            p.sendMessage("null")
        } else {
            when (args[0]) {
                args0[0] -> {
                    if (args.size == 2) {
                        Util().getVirtualInventory(p, args[1])?.openGUI()
                    }
                }
            }
        }
        return false
    }

    override fun onTabComplete(p: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        if (!p.isOp) return list
        val key = args.size - 1
        if (key == 0) {
            for (s in args0) if (s.contains(args[key])) list.add(s)
        } else when (args[0]) {
            args0[0] -> when (key) {
                1 -> {
                    val options = Util().classesToStringList("kr.kro.minestar.virtualinventory.functions.inventory")
                    for (s in options) if (s.contains(args[key])) list.add(s)
                }
            }
        }

        return list
    }
}