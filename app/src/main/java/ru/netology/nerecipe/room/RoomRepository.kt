package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

class RoomRepository(
    private val recipeDao: DAO
) : Repository {

    override val stepData: LiveData<List<Step>> = recipeDao.getALlSteps().map { list ->
        list.map { it.toModel() }
    }

    override val recipeData: LiveData<List<RecipeModel>> = recipeDao.getAllRecipes().map { list ->
        list.map { recipes ->
            recipes.recipes.toModel()
        }
    }

    override fun updateRecipe(recipe: RecipeModel) = recipeDao.updateRecipe(recipe.toEntity())

    override fun insertRecipe(recipe: RecipeModel) = recipeDao.insertRecipe(recipe.toEntity())

    override fun deleteRecipe(recipe: RecipeModel) {
        recipeDao.deleteRecipe(recipe.recipeId)
        recipeDao.deleteRecipeSteps(recipe.recipeId)
    }

    override fun isFavorite(recipeId: Long) = recipeDao.isFavorite(recipeId)

    override fun getLastRecipeId() = recipeDao.getLastRecipeId()

    override fun moveRecipe(from: RecipeModel,to: RecipeModel) {

        var tempRecipe = recipeDao.getRecipe(to.recipeId)
        recipeDao.deleteRecipe(to.recipeId)
        var newRecipe = from.copy(recipeId = to.recipeId)
        recipeDao.insertRecipe(newRecipe.toEntity())

        tempRecipe = recipeDao.getRecipe(from.recipeId)
        recipeDao.deleteRecipe(from.recipeId)
        newRecipe = to.copy(recipeId = from.recipeId)
        recipeDao.insertRecipe(newRecipe.toEntity())

        val stepsTo = recipeDao.getStepsFromRecipe(to.recipeId)
        val stepsFrom = recipeDao.getStepsFromRecipe(from.recipeId)
        recipeDao.deleteRecipeSteps(to.recipeId)
        recipeDao.deleteRecipeSteps(from.recipeId)
        stepsTo.forEach {
            recipeDao.insertStep(it.copy(idToRecipe = from.recipeId))
        }
        stepsFrom.forEach {
            recipeDao.insertStep(it.copy(idToRecipe = to.recipeId)) }

    }

    override fun moveSteps(from: Step, to: Step) {

        var tempStep = recipeDao.getStep(to.stepId)
        recipeDao.deleteStep(to.stepId)
        var newStep = from.copy(
            stepId = to.stepId,
            positionInRecipe = to.positionInRecipe
            )
        recipeDao.insertStep(newStep.toEntity())

        tempStep = recipeDao.getStep(from.stepId)
        recipeDao.deleteStep(from.stepId)
        newStep = to.copy(
            stepId = from.stepId,
            positionInRecipe = from.positionInRecipe
        )
        recipeDao.insertStep(newStep.toEntity())
    }

    override fun getStepMaxPosition(idToRecipe: Long): Int = recipeDao.getStepMaxPosition(idToRecipe)

    override fun updateStep(step: Step) = recipeDao.updateStep(step.toEntity())

    override fun insertStep(step: Step) = recipeDao.insertStep(step.toEntity())

    override fun deleteStep(step: Step) {
        recipeDao.deleteStep(step.stepId)
        recipeDao.shiftStepsInRecipe(step.positionInRecipe)
    }

    companion object {
        const val NEW_ID = 0L
    }
}
