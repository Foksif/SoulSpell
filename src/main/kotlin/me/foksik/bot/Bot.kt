package me.foksik.bot

import me.foksik.bot.command.ConsoleCommandHandler
import me.foksik.bot.manager.CommandManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.plugin.java.JavaPlugin
import javax.security.auth.login.LoginException

/**
 * Класс Bot, представляющий интеграцию с Discord API для плагина SoulSpell.
 * Этот класс управляет взаимодействием с Discord через бот-токен,
 * обновляет статус активности бота и отвечает за управление подключением и завершением работы.
 */
class Bot(private val token: String, private val plugin: JavaPlugin): ListenerAdapter() {
    private lateinit var jda: JDA
    private lateinit var consoleCommandHandler: ConsoleCommandHandler

    fun start() {
        try {
            consoleCommandHandler = ConsoleCommandHandler(plugin)

            jda = JDABuilder
                .createDefault(token)
                .addEventListeners(this)
                .setStatus(OnlineStatus.IDLE)
                .setActivity(Activity.watching("HardMS (5.2.24)"))
                .build()
                .awaitReady()

            CommandManager.initialize(plugin, jda)
            CommandManager.registerCommands(jda)

        } catch (e: LoginException) {
            throw RuntimeException("Failed login to Discord bot", e)
        }
    }

    fun updateActivity(activity: String) {
        jda.presence.setActivity(Activity.watching(activity))
    }

    fun shutdown() {
        jda.shutdown()
    }
}