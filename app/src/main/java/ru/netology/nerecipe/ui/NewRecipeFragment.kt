package ru.netology.nerecipe.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.NewRecipeFragmentBinding
import ru.netology.nerecipe.models.RecipeModel

class NewRecipeFragment :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NewRecipeFragmentBinding.inflate(inflater,container,false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.goToSteps.setOnClickListener {
            if(binding.recipeName.text.isNullOrBlank() ||
                binding.category.text.isNullOrBlank()) Snackbar.make(binding.root,
            "Recipe name or content should not be empty",Snackbar.LENGTH_SHORT).show()
            else {
                val recipe = RecipeModel(
                    id = 0L,
                    recipeName = binding.recipeName.text.toString(),
                    author = "Вставь поле автора",
                    category = binding.category.text.toString(),
                    recipeImagePath = if (binding.recipeImage.tag == null) null
                                        else binding.recipeImage.tag.toString()
                )
                viewModel.onCreateNewRecipe(recipe)
                findNavController().navigate(R.id.action_newRecipeFragment_to_addEditStepFragment)
            }
        }

        return binding.root
    }
}