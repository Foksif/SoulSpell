package me.foksik.soulSpell.command.commands

import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * Обработчик команды /broadcast для отправки сообщений в чат с префиксом.
 * Эта команда позволяет администраторам или пользователям с нужными правами отправлять сообщение в глобальный чат.
 */
object BroadcastCommand: CommandExecutor {

    // Ссылка на объект для работы с конфигурацией
    private lateinit var configUtil: ConfigUtil

    /**
     * Инициализирует объект ConfigUtil, который используется для доступа к конфигурации плагина.
     * Этот метод должен быть вызван при инициализации плагина для настройки конфигурации.
     */
    fun init(plugin: JavaPlugin) {
        configUtil = ConfigUtil(plugin)
    }

    /**
     * Обработка команды /broadcast.
     * Эта команда проверяет наличие прав у пользователя и выполняет соответствующее действие.
     * Если аргументы пустые, выводится сообщение о правильном использовании команды.
     * Если аргументы присутствуют, сообщение отправляется в чат с заданным префиксом.
     *
     * @param sender - отправитель команды (игрок или консоль)
     * @param command - команда, вызванная пользователем
     * @param label - псевдоним команды (например, /broadcast)
     * @param args - аргументы, переданные командой (например, текст сообщения)
     * @return true, если команда была успешно обработана
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // Проверяем, что команда называется "broadcast"
        if (command.name.equals("broadcast", ignoreCase = true)) {
            // Проверяем, имеет ли отправитель команду права для использования этой команды
            if (sender.hasPermission("soulspell.broadcast")) {

                // Если аргументы пусты, показываем сообщение о правильном использовании
                if (args.isEmpty()) {
                    // Получаем строку из конфигурации и отправляем её отправителю
                    val message: String = configUtil.config.getString("broadcastCommand.message.usages").toString()
                    sender.message(message)
                    return true
                }

                // Если аргументы не пусты, собираем сообщение
                val message = args.joinToString(" ")
                val prefix: String = configUtil.config.getString("broadcastCommand.message.bPrefix").toString()

                // Отправляем сообщение всем игрокам на сервере
                ChatUtil.broadcast("$prefix &f$message", Pair("{sender}", sender.name))
            } else {
                // Если у отправителя нет прав, выводим сообщение о недостаточности прав
                val message: String = configUtil.config.getString("globalMessage.noPermission").toString()
                sender.message(message)
            }
            return true
        }
        return false
    }
}