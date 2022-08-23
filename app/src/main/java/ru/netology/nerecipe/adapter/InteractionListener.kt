package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

interface InteractionListener {
    fun showDetailedView(recipe: RecipeModel)
    fun onStepDeleteClicked(step: Step)
    fun onRecipeDeleteClicked(recipe: RecipeModel)
    fun onEditClicked(step: Step)
}