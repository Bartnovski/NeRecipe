package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.models.RecipeModel

interface InteractionListener {
    fun showDetailedView(recipe: RecipeModel)
}