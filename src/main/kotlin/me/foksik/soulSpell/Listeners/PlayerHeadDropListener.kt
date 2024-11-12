package me.foksik.soulSpell.Listeners

import me.foksik.soulSpell.Utils.ChatUtil.message
import me.foksik.soulSpell.Utils.ConfigUtil
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class PlayerHeadDropListener(plugin: JavaPlugin): Listener {
    private val configUtil = ConfigUtil(plugin)

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val deadPlayer = event.entity
        val killer = deadPlayer.killer

        val chance: Double = configUtil.config.getDouble("playerHeadDrop.сhance", 0.15)

        if (killer != null && killer != deadPlayer) {
            if (Random.nextDouble() <= chance) {
                val playerHead = ItemStack(Material.PLAYER_HEAD, 1)
                val meta = playerHead.itemMeta

                if (meta is org.bukkit.inventory.meta.SkullMeta) {
                    meta.owningPlayer = deadPlayer
                    playerHead.itemMeta = meta
                }

                event.drops.add(playerHead)
                val message: String = configUtil.config.getString("playerHeadDrop.message.headDrop").toString()
                deadPlayer.message(message)
            }
        }
    }
}