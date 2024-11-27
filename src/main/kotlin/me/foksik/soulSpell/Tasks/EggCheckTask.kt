package me.foksik.soulSpell.Tasks

import me.foksik.soulSpell.SoulSpell
import me.foksik.soulSpell.Utils.ChatUtil.broadcast
import me.foksik.soulSpell.Utils.ChatUtil.message
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class EggCheckTask(private val plugin: SoulSpell) : Runnable {
    private val activeTasks = mutableMapOf<Player, BukkitRunnable>()

    override fun run() {
        if (Bukkit.getOnlinePlayers().isEmpty()) return

        Bukkit.getOnlinePlayers().forEach { player ->
            val hasDragonEgg = player.inventory.contents.any { it?.type == Material.DRAGON_EGG }
            if (hasDragonEgg) {
                giveDragonBlessing(player)
            } else {
                removeDragonBlessing(player)
            }
        }
    }

    private fun giveDragonBlessing(player: Player) {
        if (player.hasPotionEffect(PotionEffectType.GLOWING)) return
        player.addPotionEffects(
            listOf(
                PotionEffect(PotionEffectType.GLOWING, Int.MAX_VALUE, 0, true, false),
                PotionEffect(PotionEffectType.STRENGTH, Int.MAX_VALUE, 1, true, false),
                PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 0, true, false)
            )
        )

        val task = object : BukkitRunnable() {
            override fun run() {
                if (!player.isOnline || !player.inventory.contents.any { it?.type == Material.DRAGON_EGG }) {
                    cancel()
                    activeTasks.remove(player)
                    return
                }
                spawnPurpleParticles(player.location)
            }
        }
        task.runTaskTimer(plugin, 0L, 20L)
        activeTasks[player] = task

        player.message("")
        player.message("&6Вы получили благословление дракона")
        player.message("")
        broadcast("&6Игрок ${player.name} получил яйцо дракона")
    }

    private fun removeDragonBlessing(player: Player) {
        if (!player.hasPotionEffect(PotionEffectType.GLOWING)) return

        activeTasks[player]?.cancel()
        activeTasks.remove(player)

        player.removePotionEffects(
            PotionEffectType.GLOWING,
            PotionEffectType.SPEED,
            PotionEffectType.STRENGTH
        )

        player.message("")
        player.message("&cВы потеряли благословление дракона")
        player.message("")
        broadcast("&6Игрок ${player.name} потерял яйцо дракона")
    }

    private fun spawnPurpleParticles(location: Location) {
        val dustOptions = Particle.DustOptions(Color.fromRGB(128, 0, 128), 1.0f)
        location.world?.spawnParticle(Particle.DUST, location, 10, 0.5, 1.0, 0.5, 0.05, dustOptions)
    }

    private fun Player.removePotionEffects(vararg effects: PotionEffectType) {
        effects.forEach { this.removePotionEffect(it) }
    }
}
