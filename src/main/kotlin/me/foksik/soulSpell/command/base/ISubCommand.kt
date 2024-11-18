package me.foksik.soulSpell.command.base

import org.bukkit.command.CommandSender

interface ISubCommand {
    fun execute(sender: CommandSender, args: Array<out String>)
    fun canExecute(sender: CommandSender): Boolean
    fun getDescription(): String
    fun getUsage(): String
}