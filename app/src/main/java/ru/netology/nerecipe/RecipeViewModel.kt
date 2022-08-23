package ru.netology.nerecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.adapter.InteractionListener
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

     override fun showDetailedView(recipe: RecipeModel) {
          onContentClickEvent.value = recipe
     }

     override fun onStepDeleteClicked(step: Step) {
          repository.deleteStep(step)
     }

     override fun onRecipeDeleteClicked(recipe: RecipeModel) {
          repository.deleteRecipe(recipe)
     }

     override fun onEditClicked(step: Step) {
          onStepEditClickedEvent.value = step
     }


     companion object{
          var onCreatingRecipe: RecipeModel? = null
     }
}