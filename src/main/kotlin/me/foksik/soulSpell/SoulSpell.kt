package me.foksik.soulSpell

import com.myplugin.recipe.RecipesManager
import me.foksik.soulSpell.Listeners.PlayerHeadDropListener
import me.foksik.soulSpell.Listeners.PlayerSignListener
import me.foksik.soulSpell.Tasks.ActionBarCordsTask
import me.foksik.soulSpell.Utils.ConfigUtil
import me.foksik.soulSpell.command.commands.BroadcastCommand
import me.foksik.soulSpell.command.commands.HatCommand
import me.foksik.soulSpell.command.commands.SexCommand
import me.foksik.soulSpell.command.commands.SoulSpellCommand
import net.md_5.bungee.api.ChatColor
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

        SexCommand.init(this)
        HatCommand.init(this)
        BroadcastCommand.init(this)

        if (config.getBoolean("broadcastCommand.enabled")) {
            getCommand("broadcast")?.setExecutor(BroadcastCommand)
        }
        if (config.getBoolean("sexCommand.enabled")) {
            getCommand("sex")?.setExecutor(SexCommand)
        }
        if (config.getBoolean("hatCommand.enabled")) {
            getCommand("hat")?.setExecutor(HatCommand)
        }

        val soulSpellCommand = SoulSpellCommand(this)
        getCommand("soulspell")?.apply {
            setExecutor(soulSpellCommand)
            tabCompleter = soulSpellCommand
        }

        server.pluginManager.registerEvents(PlayerHeadDropListener(this), this)
        server.pluginManager.registerEvents(PlayerSignListener(this), this)

        ActionBarCordsTask(this).start()
        startUpMessage()

//        Crafts
        RecipesManager(this).registerRecipes()
    }

    override fun onDisable() {
        configUtil.saveConfig()
        logger.info("SoulSpell Disabled")
    }

    private fun startUpMessage() {
        val message = """
            ${ChatColor.GREEN}==============================
            ${ChatColor.YELLOW}╭━━━╮╱╱╱╱╱╭╮╭━━━╮╱╱╱╱╱╭╮╭╮
            ${ChatColor.YELLOW}┃╭━╮┃╱╱╱╱╱┃┃┃╭━╮┃╱╱╱╱╱┃┃┃┃
            ${ChatColor.YELLOW}┃╰━━┳━━┳╮╭┫┃┃╰━━┳━━┳━━┫┃┃┃
            ${ChatColor.YELLOW}╰━━╮┃╭╮┃┃┃┃┃╰━━╮┃╭╮┃┃━┫┃┃┃
            ${ChatColor.YELLOW}┃╰━╯┃╰╯┃╰╯┃╰┫╰━╯┃╰╯┃┃━┫╰┫╰╮
            ${ChatColor.YELLOW}╰━━━┻━━┻━━┻━┻━━━┫╭━┻━━┻━┻━╯
            ${ChatColor.YELLOW}╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃
            ${ChatColor.YELLOW}╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰╯
            
            ${ChatColor.AQUA}SoulSpell Loaded
            ${ChatColor.RED}by Foksik
            ${ChatColor.GREEN}==============================
        """.trimIndent()

        logger.info(message)
    }
}

