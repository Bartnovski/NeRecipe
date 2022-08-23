package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Embedded
import androidx.room.Relation

class Recipes (
    @Embedded val recipes : RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idToRecipe"
    )
    val steps : LiveData<List<StepEntity>>
    )
