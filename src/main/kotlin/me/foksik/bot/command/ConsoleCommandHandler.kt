package me.foksik.bot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ConsoleCommandHandler(private val plugin: JavaPlugin) {
    fun execute(command: String, event: SlashCommandInteractionEvent) {
        event.deferReply().queue { hook ->

            val consoleSender: ConsoleCommandSender = Bukkit.getServer().consoleSender
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val originalOut = System.out
            val logger = Bukkit.getLogger()

            val logHandler = object : java.util.logging.Handler() {
                override fun publish(record: java.util.logging.LogRecord) {
                    outputStream.write((record.message + "\n").toByteArray())
                }
                override fun flush() {}
                override fun close() {}
            }
            logger.addHandler(logHandler)

            System.setOut(printStream)

            Bukkit.getScheduler().runTask(plugin, Runnable {
                try {
                    val success = Bukkit.getServer().dispatchCommand(consoleSender, command)

                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        logger.removeHandler(logHandler)
                        System.setOut(originalOut)

                        val output = outputStream.toString().trim()
                        if (output.isNotEmpty()) {
                            hook.editOriginal("Команда успешно выполнена: `$command`\nРезультат:\n$output").queue()
                        } else {
                            hook.editOriginal("Команда выполнена, но результат пуст. Проверьте серверные логи.").queue()
                        }
                    }, 40L)
                } catch (e: Exception) {
                    logger.removeHandler(logHandler)
                    System.setOut(originalOut)
                    hook.editOriginal("Произошла ошибка при выполнении команды: `${e.message}`").queue()
                }
            })
        }
    }

}
