package me.foksik.soulSpell.Tasks

import me.foksik.soulSpell.Utils.CoordinatsUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable


class ActionBarCordsTask(private val plugin: JavaPlugin) {
    fun start() {
        object : BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    val itemInMainHand = player.inventory.itemInMainHand
                    val itemInOffHand = player.inventory.itemInOffHand

                    if (itemInMainHand.type.name == "COMPASS" || itemInOffHand.type.name == "COMPASS") {
                        val location = player.location
                        val message = CoordinatsUtil.formatCoordinats(location.blockX, location.blockY, location.blockZ)

                        player.spigot().sendMessage(
                            net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                            net.md_5.bungee.api.chat.TextComponent(message)
                        )
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}