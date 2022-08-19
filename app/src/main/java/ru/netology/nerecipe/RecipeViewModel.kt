package ru.netology.nerecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.adapter.InteractionListener
import ru.netology.nerecipe.demo.RecipeDemo
import ru.netology.nerecipe.demo.StepDemo
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.utils.SingleLiveEvent

class RecipeViewModel : ViewModel(), InteractionListener {

     private val repository : Repository = RecipeDemo()
     val stepData = StepDemo.stepData
     val recipeData = repository.recipeData
     val onContentClickEvent = SingleLiveEvent<RecipeModel>()

     override fun showDetailedView(recipe: RecipeModel) {
          onContentClickEvent.value = recipe
     }
}