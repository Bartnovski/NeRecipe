package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

interface InteractionListener {
    fun showDetailedView(recipe: RecipeModel)
    fun onRecipeDeleteClicked(recipe: RecipeModel)
    fun onRecipeEditClicked(recipe: RecipeModel)
    fun isFavoriteClicked(recipe: RecipeModel)

    fun moveRecipes(from: RecipeModel,to: RecipeModel)
    fun moveSteps(from: Step,to: Step)

    fun onEditClicked(step: Step)
    fun onStepDeleteClicked(step: Step)

}