package me.foksik.soulSpell.command.base

import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.commands.BroadcastCommand
import me.foksik.soulSpell.command.commands.HatCommand
import me.foksik.soulSpell.command.commands.SexCommand
import me.foksik.soulSpell.command.commands.SoulSpellCommand
import org.bukkit.plugin.java.JavaPlugin


class CommandRegister(private val plugin: JavaPlugin) {
    private val configUtil = ConfigUtil(plugin)

    private fun commandInit() {
        SexCommand.init(plugin)
        HatCommand.init(plugin)
        BroadcastCommand.init(plugin)
    }

    private fun commandRegister() {
        if (configUtil.config.getBoolean("broadcastCommand.enabled")) {
            plugin.getCommand("broadcast")?.setExecutor(BroadcastCommand)
        }
        if (configUtil.config.getBoolean("sexCommand.enabled")) {
            plugin.getCommand("sex")?.setExecutor(SexCommand)
        }
        if (configUtil.config.getBoolean("hatCommand.enabled")) {
            plugin.getCommand("hat")?.setExecutor(HatCommand)
        }

        val soulSpellCommand = SoulSpellCommand(plugin)
        plugin.getCommand("soulspell")?.apply {
            setExecutor(soulSpellCommand)
            tabCompleter = soulSpellCommand
        }
    }

    public fun init() {
        commandInit()
        commandRegister()
    }
}