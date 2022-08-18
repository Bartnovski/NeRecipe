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
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.utils.touch_helper.RecipeItemTouchHelperCallback

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecycler.adapter = adapter
        viewModel.recipeData.observe(viewLifecycleOwner) { recipe ->
            adapter.submitList(recipe)
        }

        viewModel.onContentClickEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_feedFragment_to_recipeFragment)
        }

        val callback = RecipeItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recipeRecycler)

        return binding.root
    }
}
