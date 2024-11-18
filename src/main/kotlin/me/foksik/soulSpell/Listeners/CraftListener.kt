package me.foksik.soulSpell.Listeners

import me.foksik.soulSpell.Utils.PotionUtil
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.meta.PotionMeta

class CraftListener : Listener {

    @EventHandler
    fun onPrepareItemCraft(event: PrepareItemCraftEvent) {
        val matrix = event.inventory.matrix
        val centerItem = matrix[4] ?: return

        // Проверяем, что центральный слот — это зелье
        if (centerItem.type != Material.POTION) return
        val meta = centerItem.itemMeta as? PotionMeta ?: return

        // Проверяем, что это зелье невидимости
        if (!PotionUtil.isInvisibilityPotion(meta)) {
            event.inventory.result = null  // Если это не зелье невидимости, результат будет null
        }
    }
}
