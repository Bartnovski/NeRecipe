package ru.netology.nerecipe.room

import ru.netology.nerecipe.models.RecipeModel

internal fun RecipeEntity.toModel() = RecipeModel(
    recipeId = recipeId,
    recipeName = recipeName,
    author = author,
    category = category,
    recipeImagePath = recipeImagePath,
    isFavorite = isFavorite

)

internal fun RecipeModel.toEntity() = RecipeEntity(
    recipeId = recipeId,
    recipeName = recipeName,
    author = author,
    category = category,
    recipeImagePath = recipeImagePath,
    isFavorite = isFavorite
)