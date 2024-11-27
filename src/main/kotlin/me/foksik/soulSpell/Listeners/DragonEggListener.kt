package me.foksik.soulSpell.Listeners

import me.foksik.soulSpell.SoulSpell
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable

class DragonEggListener(private val plugin: SoulSpell) : Listener {
    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player

        if (player.hasMetadata("eggGlowTask")) {
            val task = player.getMetadata("eggGlowTask")
                .firstOrNull()?.value() as? BukkitRunnable

            task?.cancel()
            player.removeMetadata("eggGlowTask", plugin)
        }

        if (player.isGlowing) {
            player.isGlowing = false
        }
        player.removePotionEffect(org.bukkit.potion.PotionEffectType.STRENGTH)
        player.removePotionEffect(org.bukkit.potion.PotionEffectType.SPEED)
    }
}