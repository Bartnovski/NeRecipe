package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {

    @Query("SELECT * FROM steps ORDER BY id DESC")
    fun getAllSteps() : LiveData<List<StepEntity>>

    @Insert
    fun insertStep(step: StepEntity)

    @Update
    fun updateStep(step: StepEntity)


    fun saveStep(step: StepEntity) {
        if (step.id == 0) insertStep(step) else updateStep(step)
    }


    @Query("DELETE FROM steps WHERE id = :id")
    fun deleteStep(id: Int)

    @Transaction
    @Query("SELECT * FROM recipe ORDER BY id DESC")
    fun getAllRecipes() : LiveData<List<Recipes>>


    @Insert
    fun insertRecipe(recipe: RecipeEntity)


    @Update
    fun updateRecipe(recipe: RecipeEntity)


    fun saveRecipe(recipe: RecipeEntity) {
        if(recipe.id == 0L) insertRecipe(recipe) else updateRecipe(recipe)
    }

    @Query("DELETE  FROM recipe WHERE id = :id")
    fun deleteRecipe(id: Long)
}