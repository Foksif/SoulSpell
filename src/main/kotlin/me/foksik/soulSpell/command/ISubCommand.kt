package me.foksik.soulSpell.command

import org.bukkit.command.CommandSender

interface ISubCommand {
    fun execute(sender: CommandSender, args: Array<out String>)
}