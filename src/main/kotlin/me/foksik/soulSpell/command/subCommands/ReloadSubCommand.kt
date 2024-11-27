package me.foksik.soulSpell.command.subCommands

import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.base.AbstractSubCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class ReloadSubCommand(plugin: JavaPlugin) : AbstractSubCommand(plugin) {
    private val configUtil = ConfigUtil(plugin)

    /**
     * Основная логика команды: перезагрузка конфигурации.
     */
    override fun onCommand(sender: CommandSender, args: Array<out String>) {
        configUtil.reloadConfig()
        sender.message("&aКонфигурация успешно перезагружена!")
    }

    /**
     * Проверка прав доступа к выполнению команды.
     * Только операторы или пользователи с разрешением `soulspell.reload` могут выполнить команду.
     */
    override fun canExecute(sender: CommandSender): Boolean {
        return sender.hasPermission("soulspell.reload") || sender.isOp
    }

    /**
     * Описание команды для документации или подсказок.
     */
    override fun getDescription(): String {
        return "Перезагружает конфигурацию плагина."
    }

    /**
     * Инструкция по использованию команды.
     */
    override fun getUsage(): String {
        return "/soulspell reload"
    }
}
