package me.foksik.soulSpell.Utils


import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.CommandSender

object ChatUtil {
    private fun format(text: String, vararg args: Pair<String, String>): String {
        return ChatColor.translateAlternateColorCodes('&', applyArgs(text, * args))
    }

    private fun applyArgs(text: String, vararg args: Pair<String, String>): String {
        var result = text
        for(arg in args) {
            result = result.replace(arg.first, arg.second)
        }
        return result
    }

    fun CommandSender.message(msg: String, vararg args: Pair<String, String>) {
        sendMessage(format(msg, *args))
    }

    fun broadcast(msg: String, vararg args: Pair<String, String>) {
        Bukkit.getOnlinePlayers().forEach {it.message(msg, *args)}
    }

    fun sendLocalMessage(location: Location, msg: String, radius: Int, vararg args: Pair<String, String>) {
        val playerInRadius = location.world?.players?.filter {
            it.location.distance(location) <= radius
        }

        playerInRadius?.forEach{ it.message(msg, *args) }
    }

}