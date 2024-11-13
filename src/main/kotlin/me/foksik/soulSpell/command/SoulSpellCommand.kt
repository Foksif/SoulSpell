package me.foksik.soulSpell.command

import me.foksik.soulSpell.command.subCommands.ReloadCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class SoulSpellCommand(plugin: JavaPlugin): CommandExecutor {
    private val subCommands = mutableMapOf<String, ISubCommand>()

    init {
        // Регистрируем подкоманды
        subCommands["reload"] = ReloadCommand(plugin)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("§eИспользование: §a/soulspell <подкоманда>")
            return true
        }

        val subCommand = subCommands[args[0].lowercase()]
        if (subCommand != null) {
            subCommand.execute(sender, args.sliceArray(1 until args.size))
        } else {
            sender.sendMessage("§cНеизвестная подкоманда. Используйте §a/soulspell <подкоманда>")
        }
        return true
    }
}