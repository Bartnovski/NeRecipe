package ru.netology.nerecipe.demo

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.models.RecipeModel

class RecipeDemo() : Repository {

    override val recipeData: LiveData<List<RecipeModel>>

    init {
        val initialRecipe = List(3) { index ->
            RecipeModel(
                id = index + 1L,
                recipeName = "Солянка",
                author = "Иван Иванов",
                recipeGroup = "Русская",
                recipeImagePath = "file:///mnt/sdcard/Pictures/солянка.jpg".toUri()
            )
        }
        recipeData = MutableLiveData(initialRecipe)
    }

}