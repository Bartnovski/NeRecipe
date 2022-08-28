package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DAO {

    @Query("SELECT * FROM steps ORDER BY stepId ASC")
    fun getALlSteps() : LiveData<List<StepEntity>>


    @Insert(entity = StepEntity::class)
    fun insertStep(step: StepEntity)


    @Query("DELETE FROM steps WHERE stepId = :stepId")
    fun deleteStep(stepId: Long)


    @Update(entity = StepEntity::class)
    fun updateStep(step: StepEntity)


    @Query("DELETE FROM steps WHERE idToRecipe = :id")
    fun deleteRecipeSteps(id: Long)



    @Query("SELECT MAX(positionInRecipe) FROM steps WHERE idToRecipe = :idToRecipe")
    fun getStepMaxPosition(idToRecipe: Long) : Int


    @Query("SELECT * FROM steps WHERE stepId = :to")
    fun getStep(to: Long) : StepEntity


    @Query("""
        UPDATE steps SET positionInRecipe = positionInRecipe - 1
        WHERE positionInRecipe > :position
        """)
    fun shiftStepsInRecipe(position: Int)


    @Query("SELECT * FROM steps WHERE idToRecipe = :recipeId")
    fun getStepsFromRecipe(recipeId: Long) : List<StepEntity>




    @Query("SELECT * FROM recipe")
    fun getAllRecipes() :LiveData<List<Recipes>>


    @Insert
    fun insertRecipe(recipe: RecipeEntity)



    @Update
    fun updateRecipe(recipe: RecipeEntity)


    @Query("""UPDATE recipe SET
                isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
            WHERE recipeId  = :recipeId
     """)
    fun isFavorite(recipeId: Long)


    @Query("DELETE  FROM recipe WHERE recipeId = :id")
    fun deleteRecipe(id: Long)


    @Query("SELECT MAX(recipeId) FROM recipe")
    fun getLastRecipeId() : Long


    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    fun getRecipe(id: Long) : RecipeEntity
}