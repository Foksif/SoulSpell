package me.foksik.soulSpell.Listeners

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ConfigUtil
import net.md_5.bungee.api.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin

class ChatListener(private val plugin: JavaPlugin, private val chatUtil: ChatUtil): Listener{
    private val configUtil = ConfigUtil(plugin)

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message

        if (message.startsWith("!")) {
            event.isCancelled = true
//            val globalMessage = "${ChatColor.RED}[Глобальный] ${player.name}: ${message.substring(1)}"
            val globalMessage: String = configUtil.config.getString("chatSettings.globalChat.format").toString()
            chatUtil.broadcast(globalMessage, Pair("{player}", player.name), Pair("{message}", message.substring(1)))
        } else {
            event.isCancelled = true
//            val localMessage = "${ChatColor.GREEN}[Локальный] ${player.name}: $message"
            val localMessage: String = configUtil.config.getString("chatSettings.localChat.format").toString()
            val radius: Int = configUtil.config.getInt("chatSettings.localChat.radius")
            chatUtil.sendLocalMessage(player.location, localMessage, radius, Pair("{player}", player.name), Pair("{message}", message))
        }
    }
}