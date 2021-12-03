package kr.kro.minestar.virtualinventory.commands

import kr.kro.minestar.virtualinventory.functions.chest.ChestList
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ChestCMD : CommandExecutor {
    private val args0 = listOf<String>()
    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (p !is Player) return false
        if (args.isEmpty()) ChestList(p)
        return false
    }
}