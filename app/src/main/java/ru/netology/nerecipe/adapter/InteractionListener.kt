package ru.netology.nerecipe.adapter

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

interface InteractionListener {
    fun showDetailedView(recipe: RecipeModel)
    fun onRecipeDeleteClicked(recipe: RecipeModel)
    fun onRecipeEditClicked(recipe: RecipeModel)

    fun onEditClicked(step: Step)
    fun onStepDeleteClicked(step: Step)

}