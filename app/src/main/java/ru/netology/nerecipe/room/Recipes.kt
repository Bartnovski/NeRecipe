package ru.netology.nerecipe.room

import androidx.room.Embedded
import androidx.room.Relation


data class Recipes(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation ( parentColumn = "id",
                entityColumn = "idToRecipe")
    val steps: List<StepEntity>
)