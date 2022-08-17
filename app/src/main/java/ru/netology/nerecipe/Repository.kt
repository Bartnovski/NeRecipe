package ru.netology.nerecipe

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

interface Repository {
    val recipeData : LiveData<List<RecipeModel>>
}