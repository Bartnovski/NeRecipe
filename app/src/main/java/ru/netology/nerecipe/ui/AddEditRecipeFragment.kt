package ru.netology.nerecipe.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.AddEditRecipeFragmentBinding

class AddEditRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddEditRecipeFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        var linkImageHolder: Uri? = null


        if (!RecipeViewModel.addingRecipeFlag) {
            Picasso.get()
                .load(viewModel.onRecipeEditClickedEvent.value?.recipeImagePath)
                .placeholder(R.drawable.ic_add_image_24dp)
                .into(binding.recipeImage)
            binding.category.setSelection(
                getSpinnerItemPosition(binding.category,viewModel.onRecipeEditClickedEvent.value!!.category)
            )
            binding.recipeImage.scaleType = ImageView.ScaleType.FIT_XY
            binding.recipeName.setText(viewModel.onRecipeEditClickedEvent.value?.recipeName)
            binding.recipeAuthor.setText(viewModel.onRecipeEditClickedEvent.value?.author)
            binding.accept.setImageResource(R.drawable.ic_ok_24dp)

        } else binding.accept.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            binding.recipeImage.setImageURI(it)
            linkImageHolder = it
        }

        binding.recipeImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.accept.setOnClickListener {

            if (!binding.recipeName.text.isNullOrBlank() &&
                !binding.recipeAuthor.text.isNullOrBlank()) {

                if (!RecipeViewModel.addingRecipeFlag) {
                    viewModel.editRecipe(binding, linkImageHolder)
                    findNavController().navigate(R.id.action_addEditRecipeFragment_to_feedFragment)

                } else {
                    viewModel.preparingNewRecipe(binding, linkImageHolder)
                    findNavController().navigate(R.id.action_addEditRecipeFragment_to_addEditStepFragment)
                }
            } else { Snackbar.make(binding.root,
                        "Название рецепта или содержимое не заполнены", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        return binding.root
    }

    private fun getSpinnerItemPosition(spinner: Spinner, category: String): Int {
        val adapter = ArrayAdapter.createFromResource(
            this.requireContext(), R.array.cuisineCategory, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter
        return adapter.getPosition(category)
    }

}