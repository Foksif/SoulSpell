package com.myplugin.recipe

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

object RecipesProvider {
    fun createInvisibleLightRecipe(plugin: JavaPlugin): ShapedRecipe {
        val key = NamespacedKey(plugin, "invisible_light")
        val result = ItemStack(Material.LIGHT, 8)

        val recipe = ShapedRecipe(key, result)
        recipe.shape("BBB", "BAB", "BBB")
        recipe.setIngredient('A', Material.POTION)  // Зелье невидимости
        recipe.setIngredient('B', Material.GLOWSTONE)  // Светокамень

        return recipe
    }
}
