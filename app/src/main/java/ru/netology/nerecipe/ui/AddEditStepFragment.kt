package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.Repository
import ru.netology.nerecipe.databinding.AddEditStepFragmentBinding
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step
import ru.netology.nerecipe.room.RoomRepository
import ru.netology.nerecipe.utils.StringArg

class AddEditStepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addEditBinding = AddEditStepFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val onClickedStep = viewModel.onStepEditClickedEvent.value

        if ((onClickedStep?.stepImagePath == null) && (onClickedStep != null)) {
            addEditBinding.imageHolder.visibility = CardView.GONE
        } else if (onClickedStep == null) {
            with(addEditBinding.stepImage) {
                setImageResource(R.drawable.ic_add_image_24dp)
            }
        } else {
            addEditBinding.stepImage.tag = onClickedStep.stepImagePath
            addEditBinding.stepImage.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get()
                .load(onClickedStep.stepImagePath)
                .into(addEditBinding.stepImage)
        }

        arguments?.textArg.let(addEditBinding.editContent::setText)

        addEditBinding.saveButton.setOnClickListener {

            if (!addEditBinding.editContent.text.isNullOrBlank()) {
                // Редактируем шаг
                if (onClickedStep != null) {

                    val step = onClickedStep.copy(
                        stepContent = addEditBinding.editContent.text.toString(),
                        stepImagePath = addEditBinding.stepImage.tag?.toString()
                    )
                    viewModel.repository.updateStep(step)
                    findNavController().navigateUp()
                        //Добаавляем шаг
                } else if (RecipeViewModel.onCreatingRecipe == null) {

                    val step = Step(
                        id = RoomRepository.NEW_ID,
                        idToRecipe = viewModel.onContentClickEvent.value!!.recipeId,
                        positionInRecipe = viewModel.repository.getStepPosition(
                            viewModel.onContentClickEvent.value!!.recipeId),
                        stepContent = addEditBinding.editContent.text.toString(),
                        stepImagePath = addEditBinding.stepImage.tag?.toString()
                    )

                } else {
                    //Создаём рецепт и добавляем 1 шаг
                    viewModel.repository.insertRecipe(RecipeViewModel.onCreatingRecipe!!)
                    val step = Step(
                        id = RoomRepository.NEW_ID,
                        idToRecipe = viewModel.repository.getLastRecipeId(),
                        positionInRecipe = 1,
                        stepContent = addEditBinding.editContent.text.toString(),
                        stepImagePath = addEditBinding.stepImage.tag?.toString()
                    )
                    viewModel.repository.insertStep(step)
                    RecipeViewModel.onCreatingRecipe = null
                    findNavController().navigate(R.id.action_addEditStepFragment_to_recipeFragment)

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
        var Bundle.textArg: String? by StringArg
    }
}