package me.foksik.bot.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color

class ServerOnlineHandler(private val plugin: JavaPlugin) {
    fun execute(event: SlashCommandInteractionEvent) {
        val onlinePlayers = plugin.server.onlinePlayers
        val playerCounter: Int = onlinePlayers.size
        val maxPlayers: Int = plugin.server.maxPlayers

        val playerList = if (onlinePlayers.isNotEmpty()) {
            onlinePlayers.joinToString(", ") { it.name }
        } else {
            "Сервер пуст."
        }

        val embed = EmbedBuilder()
            .setTitle("Онлайн сервера:  $playerCounter/$maxPlayers")
            .addField("Список игроков:", "```$playerList```", false)
            .setColor(Color(0xf5a142))
            .build()

        event.replyEmbeds(embed).setEphemeral(false).queue()
    }
}