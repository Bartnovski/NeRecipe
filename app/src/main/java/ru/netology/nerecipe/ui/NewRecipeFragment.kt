package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.NewRecipeFragmentBinding
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.room.RoomRepository

class NewRecipeFragment :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NewRecipeFragmentBinding.inflate(inflater,container,false)

        binding.goToSteps.setOnClickListener {
            if(binding.recipeName.text.isNullOrBlank()) Snackbar.make(binding.root,
            "Название рецепта или содержимое не заполнены",Snackbar.LENGTH_SHORT).show()
            else {
                val recipe = RecipeModel(
                    id = RoomRepository.NEW_ID,
                    recipeName = binding.recipeName.text.toString(),
                    author = binding.recipeAuthor.text.toString(),
                    category = binding.category.selectedItem.toString(),
                    recipeImagePath = if (binding.recipeImage.tag == null) null
                                        else binding.recipeImage.tag.toString()
                )
                RecipeViewModel.onCreatingRecipe = recipe
                findNavController().navigate(R.id.action_newRecipeFragment_to_addEditStepFragment)
            }
        }

        return binding.root
    }
}