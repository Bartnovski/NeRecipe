package ru.netology.nerecipe.models


class RecipeModel(
    val id: Long,
    val recipeName: String,
    val author: String,
    val category: String,
    val recipeImagePath: String?,
    val isFavorite: Boolean = false,
)
