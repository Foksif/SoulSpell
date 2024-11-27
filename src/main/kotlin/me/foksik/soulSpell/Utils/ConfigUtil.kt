package me.foksik.soulSpell.Utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

open class ConfigUtil(private val plugin: JavaPlugin) {

    val config: FileConfiguration
        get() = plugin.config

    fun reloadConfig() {
        plugin.reloadConfig()
    }

    fun saveConfig() {
        plugin.saveConfig()
    }

}