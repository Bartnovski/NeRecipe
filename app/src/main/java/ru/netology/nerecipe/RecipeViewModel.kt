package ru.netology.nerecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.adapter.InteractionListener
import ru.netology.nerecipe.demo.RecipeDemo
import ru.netology.nerecipe.demo.StepDemo

class RecipeViewModel : ViewModel(), InteractionListener {
     private val repository : Repository = RecipeDemo()
     val stepData = StepDemo.stepData
     val recipeData = repository.recipeData
}