package me.foksik.soulSpell.command

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

object SexCommand: CommandExecutor {

    private lateinit var configUtil: ConfigUtil

    fun init(plugin: JavaPlugin) {
        configUtil = ConfigUtil(plugin)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.hasPermission("soulspell.sexcommand")) {
                val message: String = configUtil.config.getString("globalMessage.noPermission").toString()
                sender.message(message)
                return true
            }

            val players = Bukkit.getOnlinePlayers().filter { it != sender }
            if (players.isEmpty()) {
                val message: String = configUtil.config.getString("sexCommand.message.noPlayer").toString()
                sender.message(message)
                return true
            }

            val randomPlayer = players[Random.nextInt(players.size)]

            val message: String = configUtil.config.getString("sexCommand.message.textDecoration").toString()
            ChatUtil.broadcast(message, Pair("{sender}", sender.name), Pair("{randomPlayer}", randomPlayer.name))
        } else {
            val message: String = configUtil.config.getString("globalMessage.onlyPlayer").toString()
            sender.message(message)
        }
        return true
    }
}