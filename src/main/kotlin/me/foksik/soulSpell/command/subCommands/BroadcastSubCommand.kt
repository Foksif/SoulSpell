package me.foksik.soulSpell.command.subCommands

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.base.AbstractSubCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class BroadcastSubCommand(plugin: JavaPlugin) : AbstractSubCommand(plugin) {
    private val configUtil = ConfigUtil(plugin)

    override fun onCommand(sender: CommandSender, args: Array<out String>) {
        if (args.isNotEmpty()) {
            val message = args.joinToString(" ")
            val prefix: String = configUtil.config.getString("broadcastCommand.message.bPrefix") ?: "[Broadcast]"

            ChatUtil.broadcast("$prefix &f$message", Pair("{sender}", sender.name))
        } else {
            val usageMessage: String = configUtil.config.getString("broadcastCommand.message.usages") ?: "Usage: /soulspell broadcast <Message>"
            sender.message(usageMessage)
        }
    }

    override fun canExecute(sender: CommandSender): Boolean {
        return sender.hasPermission("soulspell.broadcast") || sender.isOp
    }

    override fun getDescription(): String {
        return "Уведомление для всего сервера"
    }

    override fun getUsage(): String {
        return "/soulspell broadcast <Message>"
    }
}