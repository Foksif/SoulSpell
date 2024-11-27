package me.foksik.soulSpell.command.base

import me.foksik.api.Interface.ISubCommand
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * Абстрактный класс для подкоманд, который реализует общую логику работы с командой.
 * Все подкоманды должны наследовать этот класс и реализовывать логику самой команды.
 */
abstract class AbstractSubCommand(plugin: JavaPlugin) : ISubCommand {

    // Объект для работы с конфигурацией плагина.
    private val configUtil = ConfigUtil(plugin)

    /**
     * Метод, который выполняется при вызове подкоманды.
     * Он сначала проверяет, имеет ли отправитель команды права для выполнения команды,
     * а затем вызывает абстрактный метод для выполнения логики команды.
     *
     * @param sender - отправитель команды (игрок или консоль).
     * @param args - аргументы команды.
     */
    override fun execute(sender: CommandSender, args: Array<out String>) {
        // Проверяем права отправителя
        if (!hasPermission(sender)) {
            val noPermissionMessage = configUtil.config.getString("globalMessage.noPermission") ?: "&cУ вас нет прав для выполнения этой команды."
            sender.message(noPermissionMessage)
            return
        }

        try {
            // Выполнение команды
            onCommand(sender, args)
        } catch (e: IllegalArgumentException) {
            sender.message("§cОшибка: ${e.message}")
            sender.message("§cИспользование: ${getUsage()}")
        }
    }

    /**
     * Проверка, имеет ли отправитель право на выполнение команды.
     * Если отправитель — консоль, команда разрешена.
     * Для игроков проверяются дополнительные права через метод canExecute.
     *
     * @param sender - отправитель команды.
     * @return true, если у отправителя есть права, иначе false.
     */
    private fun hasPermission(sender: CommandSender): Boolean {
        return sender is ConsoleCommandSender || canExecute(sender)
    }

    /**
     * Абстрактный метод для выполнения логики команды.
     * Этот метод должен быть реализован в каждом подклассе, определяющем конкретную команду.
     *
     * @param sender - отправитель команды.
     * @param args - аргументы команды.
     */
    protected abstract fun onCommand(sender: CommandSender, args: Array<out String>)

    /**
     * Метод для проверки прав на выполнение команды.
     * Этот метод переопределяется в каждом подклассе, чтобы задать свои собственные условия.
     *
     * @param sender - отправитель команды.
     * @return true, если команду может выполнить данный отправитель.
     */
    override fun canExecute(sender: CommandSender): Boolean {
        return sender.isOp
    }

    /**
     * Метод для получения описания команды, если оно нужно.
     * В некоторых случаях полезно предоставлять описание команды.
     *
     * @return строка с описанием команды.
     */
    override fun getDescription(): String {
        return "None."
    }

    /**
     * Метод для получения использования команды.
     * Используется для вывода информации о правильном синтаксисе команды.
     *
     * @return строка с синтаксисом команды.
     */
    override fun getUsage(): String {
        return "/<command> <args>"
    }
}
