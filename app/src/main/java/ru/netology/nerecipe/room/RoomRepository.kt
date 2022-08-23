package ru.netology.nerecipe.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step
import java.util.*

class RoomRepository(
    private val recipeDao: DAO,
    private val stepDao: DAO
) : Repository {

    var steps: LiveData<List<StepEntity>> = recipeDao.getAllRecipes().map { entities ->
        entities.flatMap {
            it.steps
        }
    }

    override var recipeData: LiveData<List<RecipeModel>> = recipeDao.getAllRecipes().map { entities ->
        entities.map { recipes ->
            recipes.recipes.toModel()
        }
    }

    override val stepData: LiveData<List<Step>> = steps.map { steps ->
        steps.map { it.toModel() }
    }

//    init {
//        if (stepData.value.isNullOrEmpty()) {
//            val links = arrayOf(
//                "https://www.gastronom.ru/binfiles/images/20151009/b1cd0e09.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/b3076c90.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/ba6f7b51.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/b3076c90.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/ba6f7b51.jpg"
//            )
//
//            val defaultSteps = List(5) { index ->
//                Step(
//                    id = index + 1,
//                    idToRecipe = 1,
//                    stepContent =
//                    "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов" +
//                            "по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике" +
//                            "и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных" +
//                            "профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть" +
//                            "сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша" +
//                            " миссия - помочь встать на путь роста и начать цепочку перемен →",
//                    stepImagePath = links[index]
//                )
//            }
//            stepData = MutableLiveData(defaultSteps)
//        }
//    }


//    init {
//        if (recipeData.value.isNullOrEmpty()) {
//
//            val links = arrayOf(
//                "https://www.gastronom.ru/binfiles/images/20151009/b1cd0e09.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/b3076c90.jpg",
//                "https://www.gastronom.ru/binfiles/images/20151009/ba6f7b51.jpg"
//            )
//
//            val initialRecipe = List(1) { index ->
//                    RecipeModel(
//                        id = index + 1L,
//                        recipeName = "Солянка",
//                        author = "Иван Иванов",
//                        category = "Русская",
//                        recipeImagePath = links[index],
//                    )
//            }
//            recipeData = MutableLiveData(initialRecipe)
//            recipeData.value?.map { recipe ->
//                 saveRecipe(recipe)
//            }
//        }
//    }


    override fun updateRecipe(recipe: RecipeModel) = recipeDao.updateRecipe(recipe.toEntity())

    override fun insertRecipe(recipe: RecipeModel) = recipeDao.insertRecipe(recipe.toEntity())

    override fun deleteRecipe(recipe: RecipeModel) = recipeDao.deleteRecipe(recipe.recipeId)

    override fun getLastRecipeId() = recipeDao.getLastRecipeId()

    override fun saveStep(step: Step) = stepDao.saveStep(step.toEntity())

    override fun updateStep(step: Step) = stepDao.updateStep(step.toEntity())

    override fun insertStep(step: Step) = stepDao.insertStep(step.toEntity())

    override fun deleteStep(step: Step) = stepDao.deleteStep(step.id)

    companion object{
        const val NEW_ID = 0L
    }
}
