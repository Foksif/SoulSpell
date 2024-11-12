package me.foksik.soulSpell.command

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

object BroadcastCommand: CommandExecutor {

    private lateinit var configUtil: ConfigUtil

    fun init(plugin: JavaPlugin) {
        configUtil = ConfigUtil(plugin)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("broadcast", ignoreCase = true)) {
            if (sender.hasPermission("soulspell.broadcast")) {
                if (args.isEmpty()) {

                    val message: String = configUtil.config.getString("broadcastCommand.message.usages").toString()
                    sender.message(message)
                    return true
                }

                val message = args.joinToString(" ")
                val mess: String = configUtil.config.getString("broadcastCommand.message.bPrefix").toString()

                ChatUtil.broadcast("$mess &f$message", Pair("{sender}", sender.name))
            } else {
                val message: String = configUtil.config.getString("globalMessage.noPermission").toString()
                sender.message(message)
            }
            return true
        }
        return false
    }
}