package me.foksik.bot.manager

import me.foksik.bot.command.ConsoleCommandHandler
import me.foksik.bot.command.ServerOnlineHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.bukkit.plugin.java.JavaPlugin

object CommandManager: ListenerAdapter() {
    private lateinit var consoleCommandHandler: ConsoleCommandHandler
    private lateinit var serverOnlineHandler: ServerOnlineHandler

    fun initialize(plugin: JavaPlugin, jda: JDA) {
        consoleCommandHandler = ConsoleCommandHandler(plugin)
        serverOnlineHandler = ServerOnlineHandler(plugin)
        jda.addEventListener(this)
    }

    fun registerCommands(jda: JDA) {
        jda.updateCommands().addCommands(
            Commands.slash("console", "Отправить команду на сервер Minecraft")
                .addOption(OptionType.STRING, "command", "Команда для выполнения", true),
            Commands.slash("online", "Показать текущий онлайн сервера")
        ).queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "console" -> {
                val command = event.getOption("command")?.asString ?: ""
                consoleCommandHandler.execute(command, event)
            }
            "online" -> {
                serverOnlineHandler.execute(event)
            }
            else -> {
                event.reply("Неизвестная команда.").setEphemeral(true).queue()
            }
        }
    }
}