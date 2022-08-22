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
          recipeDao = AppDB.getInstance(context = application).dao,
          stepDao = AppDB.getInstance(context = application).dao
     )

     private val currentRecipe = (MutableLiveData<RecipeModel?>(null))

     val onContentClickEvent = SingleLiveEvent<RecipeModel>()
     val onStepEditClickedEvent = SingleLiveEvent<Step?>()

     override fun showDetailedView(recipe: RecipeModel) {
          onContentClickEvent.value = recipe
     }

     override fun onDeleteClicked(step: Step) {
          repository.deleteStep(step)
     }

     override fun onRecipeDeleteClicked(recipe: RecipeModel) {
          repository.deleteRecipe(recipe)
     }

     override fun onEditClicked(step: Step) {
          onStepEditClickedEvent.value = step
     }

     fun onCreateNewStep(step: Step) {
          repository.saveStep(step)
          onStepEditClickedEvent.value = null
     }

     fun onCreateNewRecipe(recipe: RecipeModel) {
          repository.saveRecipe(recipe)
     }

}