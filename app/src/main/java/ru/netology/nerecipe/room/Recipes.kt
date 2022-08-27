package ru.netology.nerecipe.room

import androidx.room.Embedded
import androidx.room.Relation

class Recipes (
    @Embedded val recipes : RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "idToRecipe"
    )
    val steps :List<StepEntity>
    )
