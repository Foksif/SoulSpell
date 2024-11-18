package com.myplugin.recipe

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

object RecipesProvider {

    fun createInvisibleLightRecipe(plugin: JavaPlugin): ShapedRecipe {
        // Создаем уникальный ключ для рецепта
        val key = NamespacedKey(plugin, "invisible_light")

        // Результат: 8 блоков света
        val result = ItemStack(Material.LIGHT, 8)

        // Создаем рецепт с использованием уникального ключа
        val recipe = ShapedRecipe(key, result)
        recipe.shape("BBB", "BAB", "BBB")
        recipe.setIngredient('A', Material.POTION)  // Зелье невидимости
        recipe.setIngredient('B', Material.GLOWSTONE)  // Светокамень

        return recipe
    }
}
