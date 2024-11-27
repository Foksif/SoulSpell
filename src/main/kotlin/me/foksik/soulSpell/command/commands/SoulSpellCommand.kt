package me.foksik.soulSpell.command.commands

import me.foksik.api.Interface.ISubCommand
import me.foksik.soulSpell.command.subCommands.BroadcastSubCommand
import me.foksik.soulSpell.command.subCommands.ReloadSubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

/**
 * Основной класс для команды SoulSpell, реализующий интерфейсы CommandExecutor и TabCompleter.
 * Этот класс управляет подкомандами плагина и их выполнением.
 */
class SoulSpellCommand(plugin: JavaPlugin) : CommandExecutor, TabCompleter {
    // Хранение подкоманд в виде мапы (ключ - имя подкоманды, значение - объект ISubCommand)
    private val subCommands = mutableMapOf<String, ISubCommand>()

    // Инициализация подкоманд
    init {
        // Регистрация подкоманды "reload", которая будет использовать класс ReloadCommand
        subCommands["reload"] = ReloadSubCommand(plugin)
        subCommands["broadcast"] = BroadcastSubCommand(plugin)
    }

    /**
     * Метод обработки основной команды. Этот метод вызывается, когда пользователь вызывает команду /soulspell.
     * В зависимости от подкоманды, будет выполнен соответствующий метод.
     *
     * @param sender - отправитель команды (игрок или консоль)
     * @param command - команда, вызванная пользователем
     * @param label - псевдоним команды (например, /ss)
     * @param args - аргументы, переданные командой (например, /soulspell reload)
     * @return true, если команда была успешно обработана
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // Если не передана подкоманда, выводим общую информацию о использовании
        if (args.isEmpty()) {
            sender.sendMessage("§eИспользование: §a/soulspell <подкоманда>")
            return true
        }

        // Проверяем, существует ли подкоманда, переданная в args[0]
        val subCommand = subCommands[args[0].lowercase()]
        if (subCommand != null) {
            // Если подкоманда существует, выполняем её
            subCommand.execute(sender, args.sliceArray(1 until args.size))
        } else {
            // Если подкоманды не существует, выводим сообщение об ошибке
            sender.sendMessage("§cНеизвестная подкоманда. Используйте §a/soulspell <подкоманда>")
        }
        return true
    }

    /**
     * Метод для автодополнения команд. Этот метод позволяет предоставить подсказки для пользователей,
     * когда они начинают вводить команду и её аргументы.
     *
     * @param sender - отправитель команды (игрок или консоль)
     * @param command - команда, для которой нужно выполнить автодополнение
     * @param alias - псевдоним команды (например, /ss)
     * @param args - аргументы, переданные командой (например, [reload])
     * @return Список строк, которые будут предложены в качестве автодополнений
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        // Если аргумент номер 1 (подкоманда) ещё не был введен, показываем все доступные подкоманды
        return when (args.size) {
            1 -> subCommands.keys.filter { it.startsWith(args[0], ignoreCase = true) }
            else -> emptyList() // Для других аргументов автодополнение не предусмотрено
        }
    }
}
