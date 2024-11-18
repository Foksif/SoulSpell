package me.foksik.soulSpell.command.commands

import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

object HatCommand: CommandExecutor {
    private lateinit var configUtil: ConfigUtil

    fun init(plugin: JavaPlugin) {
        configUtil = ConfigUtil(plugin)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player = sender
            if (!player.hasPermission("hatplugin.use")) {
                val message: String = configUtil.config.getString("globalMessage.noPermission").toString()
                player.message(message)
                return true
            }
            val itemInHand = player.inventory.itemInMainHand

            if (itemInHand == null || itemInHand.type == Material.AIR) {
                val message: String = configUtil.config.getString("hatCommand.message.noItem").toString()
                player.message(message)
                return true
            }

            val currentHelmet = player.inventory.helmet
            player.inventory.helmet = itemInHand
            player.inventory.setItemInMainHand(currentHelmet)

            val message: String = configUtil.config.getString("hatCommand.message.success").toString()
            val type: String = itemInHand.type.toString()

            player.message(message, Pair("{type}", type))

        } else {
            val message: String = configUtil.config.getString("globalMessage.onlyPlayer").toString()
            sender.message(message)
        }
        return true
    }
}