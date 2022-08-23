package ru.netology.nerecipe

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

interface Repository {
    val stepData : LiveData<List<Step>>
    val recipeData : LiveData<List<RecipeModel>>

    fun saveStep(step: Step)
    fun updateStep(step: Step)
    fun insertStep(step: Step)
    fun deleteStep(step: Step)

    fun updateRecipe(recipe: RecipeModel)
    fun insertRecipe(recipe: RecipeModel)
    fun deleteRecipe(recipe: RecipeModel)

    fun getLastRecipeId() : Long

}