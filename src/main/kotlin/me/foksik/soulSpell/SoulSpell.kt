package me.foksik.soulSpell


import me.foksik.soulSpell.Listeners.PlayerHeadDropListener
import me.foksik.soulSpell.Tasks.ActionBarCordsTask
import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.BroadcastCommand
import me.foksik.soulSpell.command.HatCommand
import me.foksik.soulSpell.command.SexCommand
import org.bukkit.plugin.java.JavaPlugin

class SoulSpell : JavaPlugin() {
    companion object {
        lateinit var instance: SoulSpell
            private set
    }

    lateinit var configUtil: ConfigUtil

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        configUtil = ConfigUtil(this)

//        ImportConfigsCommands
        SexCommand.init(this)
        HatCommand.init(this)
        BroadcastCommand.init(this)

//        Commands
        if (config.getBoolean("broadcastCommand.enabled")) {
            getCommand("broadcast")?.setExecutor(BroadcastCommand)
        }
        if (config.getBoolean("sexCommand.enabled")) {
            getCommand("sex")?.setExecutor(SexCommand)
        }
        if (config.getBoolean("hatCommand.enabled")) {
            getCommand("hat")?.setExecutor(HatCommand)
        }

//        Listeners
        server.pluginManager.registerEvents(PlayerHeadDropListener(this), this)

        ActionBarCordsTask(this).start()
    }

    override fun onDisable() {
        configUtil.saveConfig()
        logger.info("SoulSpell Disabled")
    }
}
