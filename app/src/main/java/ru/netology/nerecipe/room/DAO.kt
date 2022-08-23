package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {
    @Transaction
    @Insert(entity = StepEntity::class)
    fun insertStep(step: StepEntity)

    @Transaction
    @Update(entity = StepEntity::class)
    fun updateStep(step: StepEntity)


    fun saveStep(step: StepEntity) {
        if (step.id == 0L) insertStep(step) else updateStep(step)
    }

    @Query("DELETE FROM steps WHERE id = :id")
    fun deleteStep(id: Long)


    @Transaction
    @Query("SELECT * FROM recipe ORDER BY id DESC")
    fun getAllRecipes() : LiveData<List<Recipes>>

    @Transaction
    @Insert
    fun insertRecipe(recipe: RecipeEntity)

    @Transaction
    @Update
    fun updateRecipe(recipe: RecipeEntity)


    fun saveRecipe(recipe: RecipeEntity) {
        if(recipe.recipeId == 0L) insertRecipe(recipe) else updateRecipe(recipe)
    }

    @Query("DELETE  FROM recipe WHERE id = :id")
    fun deleteRecipe(id: Long)

    @Query("SELECT MAX(id) FROM recipe")
    fun getLastRecipeId() : Long
}