package me.foksik.api.messages

import net.md_5.bungee.api.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class PluginStatusMessages(private val plugin: JavaPlugin) {
    val logger = plugin.logger

    fun startUpMessage() {
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

    fun disabledMessage() {
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
            
            ${ChatColor.AQUA}SoulSpell Disabled
            ${ChatColor.RED}by Foksik
            ${ChatColor.GREEN}==============================
        """.trimIndent()

        logger.info(message)
    }
}