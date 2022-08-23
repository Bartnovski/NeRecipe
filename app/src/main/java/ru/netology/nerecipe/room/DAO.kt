package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DAO {
    @Transaction
    @Query("SELECT * FROM steps ORDER BY id DESC")
    fun getALlSteps() : LiveData<List<StepEntity>>

//    @Query("SELECT * FROM steps WHERE idToRecipe = :recipeId ORDER BY id DESC")
//    fun getStepsInRecipe(recipeId: Long) : LiveData<List<StepEntity>>


    @Insert(entity = StepEntity::class)
    fun insertStep(step: StepEntity)

    @Transaction
    @Update(entity = StepEntity::class)
    fun updateStep(step: StepEntity)


    @Query("DELETE FROM steps WHERE idToRecipe = :id")
    fun deleteRecipeSteps(id: Long)

    @Query("DELETE FROM steps WHERE id = :id")
    fun deleteStep(id: Long)

    @Query("SELECT MAX(positionInRecipe) FROM steps WHERE idToRecipe = :idToRecipe")
    fun getStepPosition(idToRecipe: Long) : Int






    @Transaction
    @Query("SELECT * FROM recipe")
    fun getAllRecipes() : LiveData<List<Recipes>>


    @Insert
    fun insertRecipe(recipe: RecipeEntity)

    @Transaction
    @Update
    fun updateRecipe(recipe: RecipeEntity)

    @Query("""UPDATE recipe SET
                isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
            WHERE id  = :recipeId
     """)
    fun isFavorite(recipeId: Long)

    @Query("DELETE  FROM recipe WHERE id = :id")
    fun deleteRecipe(id: Long)

    @Query("SELECT MAX(id) FROM recipe")
    fun getLastRecipeId() : Long
}