package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.demo.*
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

class RoomRepository(
    private val recipeDao: DAO
) : Repository {

    override val recipeData: LiveData<List<RecipeModel>> = recipeDao.getAllRecipes().map { list ->
       list.map { recipes ->
           recipes.recipes.toModel()
       }
   }
    override val stepData: LiveData<List<Step>> = recipeDao.getALlSteps().map { list ->
        list.map { it.toModel() }
    }

//    init {
//
//        if (recipeData.value.isNullOrEmpty()) {
//            val demoRecipes = listOf(recipeEv, recipeAs, recipePan)
//            demoRecipes.forEach { recipe ->
//                recipeDao.insertRecipe(recipe.toEntity())
//            }
//            MutableLiveData(demoRecipes)
//        }
//
//        if (stepData.value.isNullOrEmpty()) {
//            val demoSteps =
//                List(listOfStepsEv.size + listOfStepsAs.size + listOfStepsPan.size) { index ->
//                    Step(
//                        stepId = index + 1L,
//                        idToRecipe = if (index < listOfStepsEv.size) recipeEv.recipeId
//                        else if ((index >= listOfStepsEv.size) &&
//                            (index < listOfStepsEv.size + listOfStepsAs.size)
//                        ) recipeAs.recipeId
//                        else recipePan.recipeId,
//                        positionInRecipe = RoomRepository.stepPosition(index) + 1,
//                        stepContent = getContent(index),
//                        stepImagePath = getLink(index)
//                    )
//                }
//            demoSteps.forEach { step ->
//                recipeDao.insertStep(step.toEntity())
//            }
//            MutableLiveData(demoSteps)
//        }
//    }


    override fun updateRecipe(recipe: RecipeModel) = recipeDao.updateRecipe(recipe.toEntity())

    override fun insertRecipe(recipe: RecipeModel) = recipeDao.insertRecipe(recipe.toEntity())

    override fun deleteRecipe(recipe: RecipeModel) {
        recipeDao.deleteRecipe(recipe.recipeId)
        recipeDao.deleteRecipeSteps(recipe.recipeId)
    }

    override fun isFavorite(recipeId: Long) = recipeDao.isFavorite(recipeId)

    override fun getLastRecipeId() = recipeDao.getLastRecipeId()

    override fun moveRecipe(from: RecipeModel, to: RecipeModel) {

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
            recipeDao.insertStep(it.copy(idToRecipe = to.recipeId))
        }

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

    override fun getStepMaxPosition(idToRecipe: Long): Int =
        recipeDao.getStepMaxPosition(idToRecipe)

    override fun updateStep(step: Step) = recipeDao.updateStep(step.toEntity())

    override fun insertStep(step: Step) = recipeDao.insertStep(step.toEntity())

    override fun deleteStep(step: Step) {
        recipeDao.deleteStep(step.stepId)
        recipeDao.shiftStepsInRecipe(step.positionInRecipe)
    }

    companion object {
        const val NEW_ID = 0L

        private fun stepPosition(index: Int) = when (index) {
            in index until listOfStepsEv.size -> listOfStepsEv.size - (listOfStepsEv.size - index)
            in listOfStepsEv.size until listOfStepsEv.size + listOfStepsAs.size -> (listOfStepsEv.size + listOfStepsAs.size -
                    ((listOfStepsEv.size + listOfStepsAs.size) - index)) - listOfStepsEv.size
            else -> ((listOfStepsEv.size + listOfStepsAs.size + listOfStepsPan.size) -
                    ((listOfStepsEv.size + listOfStepsAs.size + listOfStepsPan.size) - index)) - (listOfStepsEv.size + listOfStepsAs.size)
        }

        private fun getContent(index: Int): String = when (index) {
            in index until listOfStepsEv.size -> listOfStepsEv[index]
            in listOfStepsEv.size until listOfStepsEv.size + listOfStepsAs.size -> listOfStepsAs[stepPosition(
                index
            )]
            else -> listOfStepsPan[stepPosition(index)]
        }

        private fun getLink(index: Int): String = when (index) {
            in index until listOfStepsEv.size -> listOfLinksEv[index]
            in listOfStepsEv.size until listOfStepsEv.size + listOfStepsAs.size -> listOfLinksAs[stepPosition(
                index
            )]
            else -> listOfLinksPan[stepPosition(index)]
        }
    }

}
