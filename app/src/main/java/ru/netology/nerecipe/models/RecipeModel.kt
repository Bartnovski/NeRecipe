package ru.netology.nerecipe.models


class RecipeModel(
    val recipeId: Long,
    val recipeName: String,
    val author: String,
    val category: String,
    val recipeImagePath: String?,
    val isFavorite: Boolean = false
)



