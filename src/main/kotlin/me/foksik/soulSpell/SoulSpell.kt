package me.foksik.soulSpell

import com.myplugin.recipe.RecipesManager
import me.foksik.api.messages.PluginStatusMessages
import me.foksik.soulSpell.Listeners.ChatListener
import me.foksik.soulSpell.Listeners.DragonEggListener
import me.foksik.soulSpell.Listeners.PlayerHeadDropListener
import me.foksik.soulSpell.Listeners.PlayerSignListener
import me.foksik.soulSpell.Tasks.ActionBarCordsTask
import me.foksik.soulSpell.Tasks.EggCheckTask
import me.foksik.soulSpell.Utils.ChatUtil
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import me.foksik.bot.Bot
import me.foksik.soulSpell.command.base.CommandRegister

/**
 * Основной класс плагина SoulSpell, наследующий JavaPlugin.
 * Этот класс отвечает за инициализацию плагина, регистрацию команд,
 * настройку событий и запуск задач.
 */
class SoulSpell : JavaPlugin() {
    companion object {
        lateinit var instance: SoulSpell
            private set
    }

    lateinit var configUtil: ConfigUtil
    lateinit var pluginStatusMessages: PluginStatusMessages
    lateinit var commandRegister: CommandRegister
    lateinit var bot: Bot

    val version = description.version
    val configVersion = config.getString("configVersion")

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

//        Инициализация переменных
        configUtil = ConfigUtil(this)
        pluginStatusMessages = PluginStatusMessages(this)
        commandRegister = CommandRegister(this)

//         Инициализация бота
        initializeBot()

//        Регистрация команд и событий
        commandRegister.init()
        registerListeners()
        startTasks()

//        Регистрируем рецепты
        RecipesManager(this).registerRecipes()
        pluginStatusMessages.startUpMessage()

        logger.warning("Plugin: $version, Config: $configVersion")
    }

    override fun onDisable() {
        configUtil.saveConfig()
        bot.shutdown()
        pluginStatusMessages.disabledMessage()
    }

    private fun initializeBot() {
        if (config.getBoolean("discordAPI.enabled")) {
            val token = config.getString("discordAPI.token")
            if (token == null) {
                logger.severe("Bot token is missing in config.")
                throw RuntimeException("Bot token is missing in config.")
            }

            bot = Bot(token, this)
            bot.start()

            startBotActivityUpdater()
        }
    }

    private fun startBotActivityUpdater() {
        val updateInterval = 20 * 60L
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            val onlinePlayers = Bukkit.getOnlinePlayers().size
            bot.updateActivity("Онлайн: $onlinePlayers")
        }, 0L, updateInterval)
    }

    private fun registerListeners() {
        val listeners = listOf(
            PlayerHeadDropListener(this),
            PlayerSignListener(this),
            ChatListener(this, ChatUtil),
            DragonEggListener(this)
        )
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }

    private fun startTasks() {
        ActionBarCordsTask(this).start()

        if (config.getBoolean("eggGlovingEvent.enabled")) {
            Bukkit.getScheduler().runTaskTimer(this, EggCheckTask(this), 20L, 20L)
            logger.info("eggGlovin listener enabled")
        }
    }
}