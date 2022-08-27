package ru.netology.nerecipe.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.AddEditStepFragmentBinding
import ru.netology.nerecipe.utils.StringArg

class AddEditStepFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addEditBinding = AddEditStepFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val onEditClickedStep  = viewModel.onStepEditClickedEvent.value
        var linkImageHolder: Uri? = null

            if (RecipeViewModel.editStepFlag) {
                Picasso.get()
                    .load(onEditClickedStep?.stepImagePath)
                    .placeholder(R.drawable.ic_logo)
                    .into(addEditBinding.stepImage)

            } else  {
                Picasso.get()
                    .load(R.drawable.ic_logo)
                    .placeholder(R.drawable.ic_logo)
                    .into(addEditBinding.stepImage)
                addEditBinding.stepImage.scaleType = ImageView.ScaleType.FIT_CENTER
            }

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            addEditBinding.stepImage.setImageURI(it)
            linkImageHolder = it
        }

        addEditBinding.stepImage.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        if (RecipeViewModel.addingRecipeFlag || RecipeViewModel.addingStepFlag) {
            addEditBinding.stepImage.setImageResource(R.drawable.ic_logo)
            addEditBinding.stepImage.scaleType = ImageView.ScaleType.FIT_CENTER
        }

        arguments?.stepArg.let(addEditBinding.editContent::setText)

        addEditBinding.saveButton.setOnClickListener {

            if (!addEditBinding.editContent.text.isNullOrBlank()) {

                // Редактирование шага
                if (RecipeViewModel.editStepFlag) {

                    viewModel.editStep(addEditBinding,linkImageHolder)
                    linkImageHolder = null
                    findNavController().navigateUp()

                        //Добаавляем шаг
                } else if (RecipeViewModel.addingStepFlag) {

                    viewModel.addStep(addEditBinding,linkImageHolder)
                    RecipeViewModel.addingStepFlag = false
                    linkImageHolder = null
                    findNavController().navigateUp()

                } else {
                    //Создаём рецепт и добавляем 1 шаг
                    viewModel.addRecipeAndFirstStep(addEditBinding,linkImageHolder)
                    linkImageHolder = null
                    findNavController().navigate(R.id.action_addEditStepFragment_to_feedFragment)
                }
            } else {
                Snackbar.make(
                    addEditBinding.root,
                    "Step content should not be empty", Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
        }
        return addEditBinding.root
    }

    companion object {
        var Bundle.stepArg: String? by StringArg
    }
}