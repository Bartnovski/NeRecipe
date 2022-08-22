package ru.netology.nerecipe.room

import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.nerecipe.models.Step

data class Recipes(
    @Embedded
    val recipe: RecipeEntity,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "idToRecipe"
    )
    val steps : List<StepEntity>
)