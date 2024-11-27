package me.foksik.soulSpell.command.commands

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object SexCommand: CommandExecutor {

    private lateinit var configUtil: ConfigUtil

    private val cooldowns: MutableMap<String, Long> = mutableMapOf()

    private var cooldown: Int = 0
    private var cooldownTime: Long = 0

    fun init(plugin: JavaPlugin) {
        configUtil = ConfigUtil(plugin)
        cooldown = configUtil.config.getInt("sexCommand.cooldown")
        cooldownTime = TimeUnit.SECONDS.toMillis(cooldown.toLong())
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.hasPermission("soulspell.sexcommand")) {
                val message: String = configUtil.config.getString("globalMessage.noPermission").toString()
                sender.message(message)
                return true
            }

            val lastUsed = cooldowns[sender.name] ?: 0
            if (System.currentTimeMillis() - lastUsed < cooldownTime) {
                val remaingCooldown = (cooldownTime - (System.currentTimeMillis() - lastUsed)) / 1000
                val cooldownMessage: String = configUtil.config.getString("sexCommand.message.cooldown").toString()

                sender.message(cooldownMessage, Pair("{time}", remaingCooldown.toString()))
                return true
            }

            cooldowns[sender.name] = System.currentTimeMillis()


            if (args.isEmpty()) {
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
                val targetName = args[0]
                val targetPlayer = Bukkit.getPlayer(targetName)
                if (targetPlayer == null || !targetPlayer.isOnline) {
                    val message: String = configUtil.config.getString("sexCommand.message.playerNotFound").toString()
                    sender.message(message)
                    return true
                }
                val message: String = configUtil.config.getString("sexCommand.message.textDecoration").toString()
                ChatUtil.broadcast(message, Pair("{sender}", sender.name), Pair("{randomPlayer}", targetPlayer.name))
            }

        } else {
            val message: String = configUtil.config.getString("globalMessage.onlyPlayer").toString()
            sender.message(message)
        }
        return true
    }
}