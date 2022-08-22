package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.adapter.StepAdapter
import ru.netology.nerecipe.databinding.RecipeLayoutBinding
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.ui.AddEditStepFragment.Companion.textArg
import ru.netology.nerecipe.utils.touch_helper.RecipeItemTouchHelperCallback

class RecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeBinding = RecipeLayoutBinding.inflate(inflater,container,false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = StepAdapter(viewModel)
        recipeBinding.stepRecycler.adapter = adapter
        viewModel.repository.stepData.observe(viewLifecycleOwner) { step ->
            adapter.submitList(step)
        }

        viewModel.onStepEditClickedEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_recipeFragment_to_addEditStepFragment,
            Bundle().apply { textArg = viewModel.onStepEditClickedEvent.value?.stepContent })
        }

        val callback = RecipeItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recipeBinding.stepRecycler)

        return recipeBinding.root
    }

}