package ru.netology.nerecipe

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import ru.netology.nerecipe.adapter.InteractionListener
import ru.netology.nerecipe.databinding.AddEditRecipeFragmentBinding
import ru.netology.nerecipe.databinding.AddEditStepFragmentBinding
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step
import ru.netology.nerecipe.room.AppDB
import ru.netology.nerecipe.room.RoomRepository
import ru.netology.nerecipe.utils.SingleLiveEvent

class RecipeViewModel(
     application: Application
) : AndroidViewModel(application), InteractionListener {



     val repository: Repository = RoomRepository(
          recipeDao = AppDB.getInstance(context = application).dao
     )

     val recipeData = repository.recipeData
     val stepData = repository.stepData

     val onContentClickEvent = SingleLiveEvent<RecipeModel>()
     val onStepEditClickedEvent = SingleLiveEvent<Step?>()
     val onDeleteRecipeClickedEvent = SingleLiveEvent<RecipeModel>()
     val onRecipeEditClickedEvent = SingleLiveEvent<RecipeModel>()

     override fun showDetailedView(recipe: RecipeModel) {
          onContentClickEvent.value = recipe
     }

     override fun onRecipeDeleteClicked(recipe: RecipeModel) {
          repository.deleteRecipe(recipe)
     }

     override fun onRecipeEditClicked(recipe: RecipeModel) {
          onRecipeEditClickedEvent.value = recipe
     }

     override fun isFavoriteClicked(recipe: RecipeModel) = repository.isFavorite(recipe.recipeId)

     override fun moveRecipes(from: RecipeModel,to: RecipeModel) = repository.moveRecipe(from,to)

     override fun moveSteps(from: Step, to: Step) = repository.moveSteps(from,to)

     fun editRecipe(binding: AddEditRecipeFragmentBinding,linkImageHolder: Uri?) {
          val recipe = RecipeModel(
               recipeId = onRecipeEditClickedEvent.value!!.recipeId,
               recipeName = binding.recipeName.text.toString(),
               category = binding.category.selectedItem.toString(),
               author = binding.recipeAuthor.text.toString(),
               recipeImagePath =  linkImageHolder?.toString() ?: onRecipeEditClickedEvent.value!!.recipeImagePath,
               isFavorite = onRecipeEditClickedEvent.value!!.isFavorite
          )
          repository.updateRecipe(recipe)
          addingRecipeFlag = false
     }

     fun preparingNewRecipe(binding: AddEditRecipeFragmentBinding,linkImageHolder: Uri?) {
          val recipe = RecipeModel(
               recipeId = RoomRepository.NEW_ID,
               recipeName = binding.recipeName.text.toString(),
               author = binding.recipeAuthor.text.toString(),
               category = binding.category.selectedItem.toString(),
               recipeImagePath = linkImageHolder?.toString()
          )
          onCreatingRecipe = recipe
          addingRecipeFlag = false
     }


     override fun onStepDeleteClicked(step: Step) {
          repository.deleteStep(step)
     }

     override fun onEditClicked(step: Step) {
          onStepEditClickedEvent.value = step
     }

     fun editStep(binding: AddEditStepFragmentBinding,linkImageHolder: Uri?){
          val step = onStepEditClickedEvent.value!!.copy(
               stepContent =   binding.editContent.text.toString(),
               stepImagePath = linkImageHolder?.toString() ?: onStepEditClickedEvent.value!!.stepImagePath
          )
          repository.updateStep(step)
          editStepFlag = false
     }

     fun addStep(binding: AddEditStepFragmentBinding,linkImageHolder: Uri?) {
          val step = Step(
               stepId = RoomRepository.NEW_ID,
               idToRecipe = onContentClickEvent.value!!.recipeId,
               positionInRecipe = repository.getStepMaxPosition(
                    onContentClickEvent.value!!.recipeId) + 1,
               stepContent = binding.editContent.text.toString(),
               stepImagePath = linkImageHolder.toString()
          )
          repository.insertStep(step)
     }

     fun addRecipeAndFirstStep(binding: AddEditStepFragmentBinding,linkImageHolder: Uri?) {
          repository.insertRecipe(onCreatingRecipe!!)

          val step = Step(
               stepId = RoomRepository.NEW_ID,
               idToRecipe = repository.getLastRecipeId(),
               positionInRecipe = 1,
               stepContent = binding.editContent.text.toString(),
               stepImagePath = linkImageHolder.toString()
          )
          repository.insertStep(step)
          onCreatingRecipe = null
          addingRecipeFlag = false
     }


     companion object{
          var onCreatingRecipe: RecipeModel? = null
          var addingStepFlag = false
          var addingRecipeFlag = false
          var editStepFlag = false
     }
}