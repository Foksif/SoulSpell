package me.foksik.soulSpell.Utils

import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffectType

object PotionUtil {

    /**
     * Проверяет, является ли зелье зельем невидимости.
     */
    fun isInvisibilityPotion(meta: PotionMeta): Boolean {
        // Проверяем, что в зелье есть эффект невидимости
        return meta.hasCustomEffects() && meta.customEffects.any { it.type == PotionEffectType.INVISIBILITY }
    }
}
