package ru.netology.nerecipe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.models.RecipeModel

class RecipeDemo() : Repository {

    override val recipeData: LiveData<List<RecipeModel>>

    val links = arrayOf(
        "https://github.com/bumptech/glide/blob/master/static/glide_logo.png?raw=true",
        "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg",
        "https://ekfgroup.com/uploads/products/EAF14169CC24DD7CE50D00A008B0D16F.jpg"
    )

    init {
        val initialRecipe = List(3) { index ->
            RecipeModel(
                id = index + 1L,
                recipeName = "Солянка",
                author = "Иван Иванов",
                recipeGroup = "Русская",
                recipeImagePath = links[index]
            )
        }
        recipeData = MutableLiveData(initialRecipe)
    }
}