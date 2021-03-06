package kr.kro.minestar.virtualchest.commands

import kr.kro.minestar.virtualchest.functions.chest.ChestMenu
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ChestCMD : CommandExecutor {
    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (p !is Player) return false
        if (args.isEmpty()) ChestMenu(p)
        return false
    }
}