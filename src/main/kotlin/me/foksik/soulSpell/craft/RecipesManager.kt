package com.myplugin.recipe

import org.bukkit.plugin.java.JavaPlugin

class RecipesManager(private val plugin: JavaPlugin) {

    fun registerRecipes() {
        // Получаем рецепт и регистрируем его
        val invisibleLightRecipe = RecipesProvider.createInvisibleLightRecipe(plugin)
        plugin.server.addRecipe(invisibleLightRecipe)
    }
}
