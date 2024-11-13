package me.foksik.soulSpell.command.subCommands

import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.ISubCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin


class ReloadCommand(plugin: JavaPlugin): ISubCommand {
    private val configUtil = ConfigUtil(plugin)

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (!sender.hasPermission("soulspell.reload")) {
            sender.message("&cУ вас нет прав для выполнения этой команды.")
            return
        }

        configUtil.reloadConfig()
        sender.message("&aКонфигурация успешно перезагружена!")
    }
}